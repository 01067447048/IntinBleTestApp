package com.jaehyeon.intinbletestapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

class ScanFragment: Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?): ScanFragment {
            val fragment = ScanFragment()
            bundle?.let {
                fragment.arguments = it
            }
            return fragment
        }
    }

}
