package com.jaehyeon.intinbletestapp.util.navi

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.ui.*
import com.jaehyeon.intinbletestapp.util.fragment.FragmentType
import com.jaehyeon.intinbletestapp.util.fragment.MainFragmentType

/**
 * Created by Jaehyeon on 2022/08/26.
 */
class FragmentNavigatorImpl(private val activity: FragmentActivity): FragmentNavigator {

    private val checkModuleFragment = CheckModuleFragment.newInstance(null)
    private val choiceDeviceFragment = ChoiceDeviceFragment.newInstance(null)
    private val choiceModuleFragment = ChoiceModuleFragment.newInstance(null)
    private val resultFragment = ResultFragment.newInstance(null)
    private val scanFragment = ScanFragment.newInstance(null)
    private val standbyModuleFragment = StandbyModuleFragment.newInstance(null)

    override fun navigateTo(screen: FragmentType) {
        replaceFragment(screen)
    }

    override fun init() {
        activity.supportFragmentManager.commit {
            add(R.id.fragment_container, checkModuleFragment)
            hide(checkModuleFragment)
            add(R.id.fragment_container, choiceDeviceFragment)
            hide(choiceDeviceFragment)
            add(R.id.fragment_container, choiceModuleFragment)
            hide(choiceModuleFragment)
            add(R.id.fragment_container, resultFragment)
            hide(resultFragment)
            add(R.id.fragment_container, scanFragment)
            hide(scanFragment)
            add(R.id.fragment_container, standbyModuleFragment)
            hide(standbyModuleFragment)
            show(choiceDeviceFragment)
        }
    }

    private fun replaceFragment(screen: FragmentType) {
        activity.supportFragmentManager.commit {
            when(screen) {
                is MainFragmentType.ScanFragmentType -> {
                    show(scanFragment)
                    hide(checkModuleFragment)
                    hide(choiceDeviceFragment)
                    hide(choiceModuleFragment)
                    hide(resultFragment)
                    hide(standbyModuleFragment)
                }
                is MainFragmentType.CheckModuleFragmentType -> {
                    show(checkModuleFragment)
                    hide(scanFragment)
                    hide(choiceDeviceFragment)
                    hide(choiceModuleFragment)
                    hide(resultFragment)
                    hide(standbyModuleFragment)
                }
                is MainFragmentType.ChoiceDeviceFragmentType -> {
                    show(choiceDeviceFragment)
                    hide(checkModuleFragment)
                    hide(scanFragment)
                    hide(choiceModuleFragment)
                    hide(resultFragment)
                    hide(standbyModuleFragment)
                }
                is MainFragmentType.ChoiceModuleFragmentType -> {
                    show(choiceModuleFragment)
                    hide(checkModuleFragment)
                    hide(choiceDeviceFragment)
                    hide(scanFragment)
                    hide(resultFragment)
                    hide(standbyModuleFragment)
                }
                is MainFragmentType.ResultFragmentType -> {
                    show(resultFragment)
                    hide(checkModuleFragment)
                    hide(choiceDeviceFragment)
                    hide(choiceModuleFragment)
                    hide(scanFragment)
                    hide(standbyModuleFragment)
                }
                is MainFragmentType.StandbyModuleFragmentType -> {
                    show(standbyModuleFragment)
                    hide(checkModuleFragment)
                    hide(choiceDeviceFragment)
                    hide(choiceModuleFragment)
                    hide(resultFragment)
                    hide(scanFragment)
                }
            }
        }
    }
}