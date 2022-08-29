package com.jaehyeon.intinbletestapp.ui.scan

import android.Manifest
import android.bluetooth.BluetoothProfile
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jaehyeon.intinbletestapp.MainState
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentScanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment: Fragment() {

    private lateinit var binding: FragmentScanBinding
    private val activityViewModel: MainViewModel by activityViewModels()
    private val adapter = ScanAdapter {
        activityViewModel.connect(it)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan, container, false)
        binding.recycler.apply {
            this.adapter = this@ScanFragment.adapter
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        activityViewModel.startScan()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel.scanBle.observe(viewLifecycleOwner) {
            adapter.addResult(it)
        }
        activityViewModel.connectState.observe(viewLifecycleOwner) {
            if (BluetoothProfile.STATE_CONNECTED == it)
                activityViewModel.runState(MainState.ChoiceDevice)
            else
                Snackbar.make(requireContext(), binding.root, "되냐?", Snackbar.LENGTH_SHORT)
        }
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
