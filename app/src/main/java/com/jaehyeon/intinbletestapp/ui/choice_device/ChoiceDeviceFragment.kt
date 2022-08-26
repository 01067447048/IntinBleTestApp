package com.jaehyeon.intinbletestapp.ui.choice_device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jaehyeon.intinbletestapp.MainState
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentChoiceDeviceBinding

class ChoiceDeviceFragment: Fragment() {

    private lateinit var binding: FragmentChoiceDeviceBinding
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choice_device, container, false)

        binding.airCare.setOnClickListener {
            activityViewModel.runState(MainState.ChoiceModule)
        }

        return binding.root
    }

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
