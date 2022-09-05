package com.jaehyeon.intinbletestapp.ui.choice_device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.jaehyeon.intinbletestapp.MainState
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentChoiceDeviceBinding
import com.jaehyeon.intinbletestapp.util.device.DeviceType
import com.jaehyeon.intinbletestapp.util.device.SendMessageType
import com.jaehyeon.intinbletestapp.util.event.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoiceDeviceFragment: Fragment() {

    private lateinit var binding: FragmentChoiceDeviceBinding
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: ChoiceDeviceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choice_device, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.action.observe(viewLifecycleOwner, EventObserver {
            when(it) {
                ChoiceDeviceState.OnClickAirCare -> {
                    activityViewModel.device = DeviceType.AirCare
                    activityViewModel.runState(MainState.ChoiceModule)
                }
                ChoiceDeviceState.OnClickAirMonitor -> {
                    activityViewModel.device = DeviceType.AirMonitor
                    Toast.makeText(requireContext(), "사용불가!", Toast.LENGTH_SHORT).show()
//                    activityViewModel.runState(MainState.ChoiceModule)
                }
            }
        })
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
