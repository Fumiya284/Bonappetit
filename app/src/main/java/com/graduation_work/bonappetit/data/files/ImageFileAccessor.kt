package com.graduation_work.bonappetit.data.files

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.R

class ImageFileAccessor(private val app: MyApplication) {
	private val format = MyApplication.imageFileFormat
	private val quality = MyApplication.imageQuality
	private val defaultImage = BitmapFactory.decodeResource(app.resources, R.drawable.no_image)
	
	fun write(image: Bitmap, filename: String) {
		app.openFileOutput("$filename.${format.name}", Context.MODE_PRIVATE).use {
			image.compress(format, quality, it)
		}
	}
	
	fun read(filename: String): Bitmap {
		app.openFileInput("$filename.${format.name}").use {
			return BitmapFactory.decodeStream(it) ?: defaultImage
		}
	}
}