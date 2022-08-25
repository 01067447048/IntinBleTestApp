package com.jaehyeon.intinbletestapp.util.navi

import com.jaehyeon.intinbletestapp.util.fragment.FragmentType

/**
 * Created by Jaehyeon on 2022/08/25.
 */
interface FragmentNavigator {
    fun navigateTo(screen: FragmentType)
    fun init()
}