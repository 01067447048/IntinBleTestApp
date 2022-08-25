package com.jaehyeon.intinbletestapp.util.navi

import com.jaehyeon.intinbletestapp.util.fragment.MainFragmentType

/**
 * Created by Jaehyeon on 2022/08/25.
 */
interface FragmentNavigator {
    fun navigateTo(screen: MainFragmentType)
    fun init()
}