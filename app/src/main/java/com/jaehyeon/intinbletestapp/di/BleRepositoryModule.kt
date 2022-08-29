package com.jaehyeon.intinbletestapp.di

import com.jaehyeon.intinbletestapp.util.ble.BleRepository
import com.jaehyeon.intinbletestapp.util.ble.BleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Jaehyeon on 2022/08/28.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class BleRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBleRepository(scanner: BleRepositoryImpl): BleRepository
}