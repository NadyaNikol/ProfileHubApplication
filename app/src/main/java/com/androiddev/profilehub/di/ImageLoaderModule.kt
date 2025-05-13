package com.androiddev.profilehub.di

import com.androiddev.profilehub.ui.imageLoader.GlideImageLoader
import com.androiddev.profilehub.ui.imageLoader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Nadya N. on 13.05.2025.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class ImageLoaderModule {

    @Binds
    @Singleton
    abstract fun bindImageLoader(impl: GlideImageLoader): ImageLoader
}