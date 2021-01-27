package com.joesemper.githubclient.ui.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.joesemper.githubclient.mvp.model.image.IImageLoader

class GlideImageLoader : IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)

    }
}