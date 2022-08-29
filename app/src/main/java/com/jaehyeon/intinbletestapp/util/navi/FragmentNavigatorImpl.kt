package com.jaehyeon.intinbletestapp.util.navi

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.ui.check_module.CheckModuleFragment
import com.jaehyeon.intinbletestapp.ui.choice_device.ChoiceDeviceFragment
import com.jaehyeon.intinbletestapp.ui.choice_module.ChoiceModuleFragment
import com.jaehyeon.intinbletestapp.ui.result.ResultFragment
import com.jaehyeon.intinbletestapp.ui.scan.ScanFragment
import com.jaehyeon.intinbletestapp.ui.standby_module.StandbyModuleFragment
import com.jaehyeon.intinbletestapp.util.fragment.MainFragmentType

/**
 * Created by Jaehyeon on 2022/08/26.
 */
class FragmentNavigatorImpl(private val activity: FragmentActivity) : FragmentNavigator {

    private val checkModuleFragment = CheckModuleFragment.newInstance(null)
    private val choiceDeviceFragment = ChoiceDeviceFragment.newInstance(null)
    private val choiceModuleFragment = ChoiceModuleFragment.newInstance(null)
    private val resultFragment = ResultFragment.newInstance(null)
    private val scanFragment = ScanFragment.newInstance(null)
    private val standbyModuleFragment = StandbyModuleFragment.newInstance(null)

    override fun navigateTo(screen: MainFragmentType) {
        replaceFragment(screen)
    }

    override fun init() {
        activity.supportFragmentManager.commit {
            replace(R.id.fragment_container, scanFragment)
        }
    }

    private fun replaceFragment(screen: MainFragmentType) {

        activity.supportFragmentManager.commit {
            when (screen) {
                MainFragmentType.Scan -> {
                    replace(R.id.fragment_container, scanFragment)
//                    hide(checkModuleFragment)
//                    hide(choiceDeviceFragment)
//                    hide(choiceModuleFragment)
//                    hide(resultFragment)
//                    hide(standbyModuleFragment)
                }
                MainFragmentType.CheckModule -> {
                    replace(R.id.fragment_container, checkModuleFragment)
//                    hide(scanFragment)
//                    hide(choiceDeviceFragment)
//                    hide(choiceModuleFragment)
//                    hide(resultFragment)
//                    hide(standbyModuleFragment)
                }
                MainFragmentType.ChoiceDevice -> {
                    replace(R.id.fragment_container, choiceDeviceFragment)
//                    hide(checkModuleFragment)
//                    hide(scanFragment)
//                    hide(choiceModuleFragment)
//                    hide(resultFragment)
//                    hide(standbyModuleFragment)
                }
                MainFragmentType.ChoiceModule -> {
                    replace(R.id.fragment_container, choiceModuleFragment)
//                    hide(checkModuleFragment)
//                    hide(choiceDeviceFragment)
//                    hide(scanFragment)
//                    hide(resultFragment)
//                    hide(standbyModuleFragment)
                }
                MainFragmentType.Result -> {
                    replace(R.id.fragment_container, resultFragment)
//                    hide(checkModuleFragment)
//                    hide(choiceDeviceFragment)
//                    hide(choiceModuleFragment)
//                    hide(scanFragment)
//                    hide(standbyModuleFragment)
                }
                MainFragmentType.StandbyModule -> {
                    replace(R.id.fragment_container, standbyModuleFragment)
//                    hide(checkModuleFragment)
//                    hide(choiceDeviceFragment)
//                    hide(choiceModuleFragment)
//                    hide(resultFragment)
//                    hide(scanFragment)

                }
            }
        }
    }
}