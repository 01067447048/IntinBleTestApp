package com.jaehyeon.intinbletestapp.di

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Jaehyeon on 2022/08/28.
 */
@Module
@InstallIn(SingletonComponent::class)
object BleAdapterModule {

    @Provides
    @Singleton
    fun provideBleAdapter(app: Application): BluetoothAdapter {
        return (app.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }


}