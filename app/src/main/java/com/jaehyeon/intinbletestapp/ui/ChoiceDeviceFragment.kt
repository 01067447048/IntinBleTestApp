package com.jaehyeon.intinbletestapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

class ChoiceDeviceFragment: Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?): ChoiceDeviceFragment {
            val fragment = ChoiceDeviceFragment()
            bundle?.let {
                fragment.arguments = it
            }
            return fragment
        }
    }

}
