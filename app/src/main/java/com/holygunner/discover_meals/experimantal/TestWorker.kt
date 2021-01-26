package com.holygunner.discover_meals.experimantal

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import timber.log.Timber

class TestWorker (appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    companion object {
        const val INPUT_1_KEY = "INPUT_1_KEY"
        const val OUTPUT_1_KEY = "OUTPUT_1_KEY"
    }

    override fun doWork(): Result {
        val strInput =
            inputData.getString(INPUT_1_KEY) ?: return Result.failure()

        Timber.d("TestWorker: performing doWork()... INPUT -> $strInput")

        val dataOutput : Data = workDataOf(OUTPUT_1_KEY to "value sample of the output data")
//        return Result.success()
        return Result.success(dataOutput)
    }
}