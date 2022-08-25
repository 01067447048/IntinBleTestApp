package com.jaehyeon.intinbletestapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Jaehyeon on 2022/08/26.
 */
class ResultFragment: Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?): ResultFragment {
            val fragment = ResultFragment()
            bundle?.let {
                fragment.arguments = it
            }
            return fragment
        }
    }
}