package com.jaehyeon.intinbletestapp.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jaehyeon on 2022/08/26.
 */
@AndroidEntryPoint
class ResultFragment: Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        return binding.root
    }

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