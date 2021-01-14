package com.holygunner.discover_meals.ui.meals_result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holygunner.discover_meals.models.Ingredient
import com.holygunner.discover_meals.models.Meal
import com.holygunner.discover_meals.business_logic.MealsFilter
import com.holygunner.discover_meals.repository.DbWrapper
import com.holygunner.discover_meals.repository.ISaver
import com.holygunner.discover_meals.repository.network.BackendAPI
import com.holygunner.discover_meals.repository.network.BackendApiManager
import io.realm.RealmResults
import kotlinx.coroutines.*
import javax.inject.Inject

class MealsResultViewModel @Inject constructor(
    val saver: ISaver,
    private val backendApiManager: BackendApiManager,
    val dbWrapper: DbWrapper
    ) : ViewModel() {
    var mealsLiveData : MutableLiveData<List<Meal>> = MutableLiveData()
    var progressLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var viewModelJob = Job()
    private var startLoad = true

    private val ioScope = CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO)

    fun loadData(ingredients: Array<Ingredient>) {
        if (startLoad) {
            viewModelScope.launch {
                updProgress(true)
                val storedIngrsMap : HashMap<String, Ingredient> = hashMapOf()
                val storedIngrs: RealmResults<Ingredient> = dbWrapper.getAllFetchedIngredientsAsync()
                storedIngrs.load()

                for (storedIngr in storedIngrs) {
                    val name = storedIngr.name
                    if (storedIngr.assosiatedMeals.size > 0)
                        name?.let { storedIngrsMap.put(it, storedIngr) }
                }

                var indx = 0
                for (ingr in ingredients) {
                    val ingrName = ingr.name
                    var isBackendFetchedStep = false
                    val meals : Array<Meal> = if (storedIngrsMap.containsKey(ingrName)) {
                        val storedInrg : Ingredient = storedIngrsMap[ingrName]!!
                        storedInrg.assosiatedMeals.toTypedArray()
                    } else {
                        isBackendFetchedStep = true
                        withContext(Dispatchers.IO) {
                            backendApiManager.filterAsync(ingrName!!).meals
                        }
                    }
                    for (meal in meals) {
                        meal.addIngredient(ingr)
                    }
                    if (meals.isNotEmpty())
                        storeIngrsWithAssociatedMeals(ingr, meals.toList())

                    indx += 1
                    val progress = indx < ingredients.size
                    updProgress(progress)

                    addMeals(meals.toMutableList())

                    if (!progress)
                        startLoad = false
                    if (isBackendFetchedStep)
                        delay(50)
                }
            }
        }
    }

    fun isDataLoadAlready() : Boolean {
        return if (progressLiveData != null && progressLiveData.value != null)
            progressLiveData.value!!
        else
            false
    }

    private fun updProgress(isOnProgress: Boolean) {
        progressLiveData.postValue(isOnProgress)
    }

    suspend fun addMeals(meals: MutableList<Meal>) {
        val data: List<Meal>? = mealsLiveData.value
        if (data != null)
            meals.addAll(data)
        val filteredMeals = MealsFilter().filterMeals(meals)
        mealsLiveData.postValue(filteredMeals)
    }

    private suspend fun storeIngrsWithAssociatedMeals(ingredient: Ingredient,
                                              meals: List<Meal>,
                                              isFetched : Boolean = true) {
        val ingredientToSave = Ingredient(name = ingredient.name,
            category = ingredient.category,
            isFill = ingredient.isFill,
            measure = ingredient.measure,
            id = ingredient.id,
            description = ingredient.description,
            type = ingredient.type
        )
        ingredientToSave.isFetched = isFetched
        ingredientToSave.assosiatedMeals.addAll(meals)
        dbWrapper.storeIngrsWithAssociatedMeals(ingredientToSave)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}