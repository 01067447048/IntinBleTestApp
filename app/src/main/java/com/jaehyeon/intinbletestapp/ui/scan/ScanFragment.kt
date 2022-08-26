package com.jaehyeon.intinbletestapp.ui.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentScanBinding

class ScanFragment: Fragment() {

    private lateinit var binding: FragmentScanBinding
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan, container, false)
        return binding.root
    }

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
