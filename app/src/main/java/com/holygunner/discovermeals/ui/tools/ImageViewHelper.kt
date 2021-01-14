package com.holygunner.discovermeals.ui.tools

import android.graphics.*
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.holygunner.discovermeals.models.Ingredient
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import com.squareup.picasso.Transformation


object ImageViewHelper {
    private const val RATIO = 32f

    fun bindIngredientWithImageView(imageView: ImageView, ingredient: Ingredient) {
        val path = buildAccessPath(ingredient.name, ingredient.category)
        Picasso.get()
            .load(path)
            .priority(Picasso.Priority.HIGH)
            //                .resize(175, 175)
            .into(imageView)
    }

    fun loadToImageView(target: Target, pictureUrl: String?) {
        if (pictureUrl != null)
            Picasso
                .get()
                .load(pictureUrl)
                .priority(Picasso.Priority.HIGH)
                .into(target)
    }

    fun loadToImageView(imageView: ImageView, pictureUrl: String?) {
        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                if (bitmap != null) {
                    val roundedBitmap = createRoundedBitmap(bitmap)
                    imageView.setImageBitmap(roundedBitmap)
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                e?.printStackTrace()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                if (placeHolderDrawable != null)
                    imageView.setImageDrawable(placeHolderDrawable)
            }
        }

        if (pictureUrl != null)
            Picasso
                .get()
                .load(pictureUrl)
                .priority(Picasso.Priority.HIGH)
                .into(target)
    }

    private fun buildAccessPath(fileName: String?, folderName: String?): String {
        return "file:///android_asset/$folderName/$fileName.png"
    }

    fun buildMissedIngrImageUrl(name: String): String {
        return "https://www.themealdb.com/images/ingredients/$name.png"
    }

    fun createRoundedBitmap(bitmap: Bitmap) : Bitmap {
        val outBitmap = Bitmap.createBitmap(
            bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(outBitmap)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())
        val rectF = RectF(rect)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, RATIO, RATIO, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return outBitmap
    }
}