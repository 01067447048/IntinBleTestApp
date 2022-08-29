package com.jaehyeon.intinbletestapp.ui.choice_module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.jaehyeon.intinbletestapp.MainState
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentChoiceModuleBinding
import com.jaehyeon.intinbletestapp.util.device.ModuleType
import com.jaehyeon.intinbletestapp.util.device.SendMessageType
import com.jaehyeon.intinbletestapp.util.event.EventObserver
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jaehyeon on 2022/08/26.
 */
@AndroidEntryPoint
class ChoiceModuleFragment: Fragment() {

    private lateinit var binding: FragmentChoiceModuleBinding
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: ChoiceModuleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choice_module, container, false)
        binding.vm = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.action.observe(viewLifecycleOwner, EventObserver {
            when(it) {
                ChoiceModuleState.OnClickLED -> activityViewModel.module = ModuleType.LED
                ChoiceModuleState.OnClickNeb -> activityViewModel.module = ModuleType.Nebulizer
                ChoiceModuleState.OnClickSpirometry -> activityViewModel.module = ModuleType.Spirometery
                ChoiceModuleState.OnClickSpray -> activityViewModel.module = ModuleType.Spray
                ChoiceModuleState.OnClickSuction -> activityViewModel.module = ModuleType.Suction
            }
            activityViewModel.sendMessage(SendMessageType.Mod)
//            activityViewModel.runState(MainState.CheckModule)
        })

        activityViewModel.receiveRead.observe(viewLifecycleOwner) {
            Snackbar.make(requireContext(), binding.root, "Read: $it", Snackbar.LENGTH_LONG)
        }

        activityViewModel.receiveWrite.observe(viewLifecycleOwner) {
            Snackbar.make(requireContext(), binding.root, "Write: $it", Snackbar.LENGTH_LONG)
        }

        activityViewModel.receiveChanged.observe(viewLifecycleOwner) {
            Snackbar.make(requireContext(), binding.root, "Chagned: $it", Snackbar.LENGTH_LONG)
        }
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