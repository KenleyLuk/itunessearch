package com.example.itunes_search.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.itunes_search.R

@BindingAdapter("imageUrl", requireAll = false)
fun setImageUrl(view: ImageView, url: String?) {
    if (url != null && url != "") {
        Glide.with(view.context).load(url).apply(
            RequestOptions().placeholder(R.color.colorGreyLight)
        ).into(view)
        view.invalidate()
    }
}