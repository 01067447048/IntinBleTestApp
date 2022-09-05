package com.jaehyeon.intinbletestapp

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.jaehyeon.intinbletestapp.databinding.ActivityMainBinding
import com.jaehyeon.intinbletestapp.util.event.EventObserver
import com.jaehyeon.intinbletestapp.util.fragment.MainFragmentType
import com.jaehyeon.intinbletestapp.util.navi.FragmentNavigatorImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private val navi = FragmentNavigatorImpl(this)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            navi.init()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        ))

    }

    override fun onStart() {
        super.onStart()
        viewModel.ui.observe(this, EventObserver {
            when(it) {
                MainState.CheckModule -> {
                    navi.navigateTo(MainFragmentType.CheckModule)
                    viewModel.currentScreen = MainFragmentType.CheckModule
                }
                MainState.ChoiceDevice -> {
                    navi.navigateTo(MainFragmentType.ChoiceDevice)
                    viewModel.currentScreen = MainFragmentType.ChoiceDevice
                }
                MainState.ChoiceModule -> {
                    navi.navigateTo(MainFragmentType.ChoiceModule)
                    viewModel.currentScreen = MainFragmentType.ChoiceModule
                }
                MainState.Result -> {
                    navi.navigateTo(MainFragmentType.Result)
                    viewModel.currentScreen = MainFragmentType.Result
                }
                MainState.Scan -> {
                    navi.navigateTo(MainFragmentType.Scan)
                    viewModel.currentScreen = MainFragmentType.Scan
                }
                MainState.StandbyModule -> {
                    navi.navigateTo(MainFragmentType.StandbyModule)
                    viewModel.currentScreen = MainFragmentType.StandbyModule
                }
                is MainState.BackState -> {

                }
            }
        })
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        if (viewModel.currentScreen == MainFragmentType.Scan)
            super.onBackPressed()
        else
            viewModel.backState()
    }

}