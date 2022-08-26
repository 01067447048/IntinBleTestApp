package com.jaehyeon.intinbletestapp.ui.choice_module

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
import com.jaehyeon.intinbletestapp.databinding.FragmentChoiceModuleBinding

/**
 * Created by Jaehyeon on 2022/08/26.
 */
class ChoiceModuleFragment: Fragment() {

    private lateinit var binding: FragmentChoiceModuleBinding
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choice_module, container, false)

        binding.lightInresultView5.setOnClickListener {
            activityViewModel.runState(MainState.CheckModule)
        }

        return binding.root
    }

    companion object {
        fun newInstance(bundle: Bundle?): ChoiceModuleFragment {
            val fragment = ChoiceModuleFragment()
            bundle?.let {
                fragment.arguments = it
            }
            return fragment
        }
    }
}