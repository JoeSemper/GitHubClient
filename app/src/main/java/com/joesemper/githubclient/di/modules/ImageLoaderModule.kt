package com.joesemper.githubclient.di.modules

import android.widget.ImageView
import com.joesemper.githubclient.mvp.model.image.IImageLoader
import com.joesemper.githubclient.ui.image.GlideImageLoader
import dagger.Module
import dagger.Provides

@Module
class ImageLoaderModule {

    @Provides
    fun getImageLoader(): IImageLoader<ImageView> = GlideImageLoader()
}