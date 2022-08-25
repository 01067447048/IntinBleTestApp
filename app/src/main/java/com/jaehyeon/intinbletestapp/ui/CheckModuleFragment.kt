package com.jaehyeon.intinbletestapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Jaehyeon on 2022/08/26.
 */
class CheckModuleFragment: Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?): CheckModuleFragment {
            val fragment = CheckModuleFragment()
            bundle?.let {
                fragment.arguments = it
            }
            return fragment
        }
    }
}