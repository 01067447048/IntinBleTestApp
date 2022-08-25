package com.jaehyeon.intinbletestapp.util.fragment

import androidx.fragment.app.Fragment
import com.jaehyeon.intinbletestapp.ui.*

sealed class MainFragmentType(fragment: Fragment): FragmentType {

    class ScanFragmentType(scanFragment: ScanFragment): MainFragmentType(scanFragment)
    class ChoiceDeviceFragmentType(fragment: ChoiceDeviceFragment): MainFragmentType(fragment)
    class CheckModuleFragmentType(fragment: CheckModuleFragment): MainFragmentType(fragment)
    class ChoiceModuleFragmentType(fragment: ChoiceModuleFragment): MainFragmentType(fragment)
    class ResultFragmentType(fragment: ResultFragment): MainFragmentType(fragment)
    class StandbyModuleFragmentType(fragment: StandbyModuleFragment): MainFragmentType(fragment)
}
