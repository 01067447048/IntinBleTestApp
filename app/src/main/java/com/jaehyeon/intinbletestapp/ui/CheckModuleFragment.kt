package com.jaehyeon.intinbletestapp.ui

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
import com.jaehyeon.intinbletestapp.databinding.FragmentCheckModuleBinding

/**
 * Created by Jaehyeon on 2022/08/26.
 */
class CheckModuleFragment: Fragment() {

    private lateinit var binding: FragmentCheckModuleBinding
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_module, container, false)

        binding.test.setOnClickListener {
            activityViewModel.runState(MainState.ChoiceDevice)
        }
        return binding.root
    }

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