package com.graduation_work.bonappetit.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object Util {
	@JvmStatic
	@BindingAdapter("imageUrl")
	fun ImageView.loadImage(url: String?) {
		Picasso.get().load(url).into(this)
	}
}