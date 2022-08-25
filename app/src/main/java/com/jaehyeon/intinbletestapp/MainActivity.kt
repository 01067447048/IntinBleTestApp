package com.jaehyeon.intinbletestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.jaehyeon.intinbletestapp.databinding.ActivityMainBinding
import com.jaehyeon.intinbletestapp.util.event.EventObserver
import com.jaehyeon.intinbletestapp.util.fragment.MainFragmentType
import com.jaehyeon.intinbletestapp.util.navi.FragmentNavigatorImpl

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navi = FragmentNavigatorImpl(this)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navi.init()
    }

    override fun onStart() {
        super.onStart()
        viewModel.ui.observe(this, EventObserver {
            when(it) {
                MainState.CheckModule -> navi.navigateTo(MainFragmentType.CheckModule)
                MainState.ChoiceDevice -> navi.navigateTo(MainFragmentType.ChoiceDevice)
                MainState.ChoiceModule -> navi.navigateTo(MainFragmentType.ChoiceModule)
                MainState.Result -> navi.navigateTo(MainFragmentType.Result)
                MainState.Scan -> navi.navigateTo(MainFragmentType.Scan)
                MainState.StandbyModule -> navi.navigateTo(MainFragmentType.StandbyModule)
            }
        })
    }

}