package com.jaehyeon.intinbletestapp.ui.standby_module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentStandbyModuleBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jaehyeon on 2022/08/26.
 */
@AndroidEntryPoint
class StandbyModuleFragment: Fragment() {

    private lateinit var binding: FragmentStandbyModuleBinding
    private val activityViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance(bundle: Bundle?): StandbyModuleFragment {
            val fragment = StandbyModuleFragment()
            bundle?.let {
                fragment.arguments = it
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_standby_module, container, false)
        return binding.root
    }

}