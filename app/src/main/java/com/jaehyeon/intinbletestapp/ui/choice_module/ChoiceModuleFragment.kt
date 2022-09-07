package com.jaehyeon.intinbletestapp.ui.choice_module

import android.os.Bundle
import android.util.Log
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
import com.jaehyeon.intinbletestapp.databinding.FragmentChoiceModuleBinding
import com.jaehyeon.intinbletestapp.ui.standby_module.StandbyState
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
                ChoiceModuleState.OnClickSpirometry -> {
                    activityViewModel.module = ModuleType.Spirometery
                    activityViewModel.sendMessage(SendMessageType.Mod)
                }
                else -> {
                    Toast.makeText(requireContext(), "사용 불가!", Toast.LENGTH_SHORT).show()
                }
                // 다른 모듈도 사용 할 수 있을 변경 되어야 함.
//                ChoiceModuleState.OnClickLED -> activityViewModel.module = ModuleType.LED
//                ChoiceModuleState.OnClickNeb -> activityViewModel.module = ModuleType.Nebulizer
//
//                ChoiceModuleState.OnClickSpray -> activityViewModel.module = ModuleType.Spray
//                ChoiceModuleState.OnClickSuction -> activityViewModel.module = ModuleType.Suction
            }
//            activityViewModel.sendMessage(SendMessageType.Mod)
        })

        activityViewModel.receiveChanged.observe(viewLifecycleOwner) {
            Log.e(javaClass.simpleName, "onViewCreated: $it", )
            if (it == "Recv Data From APP") activityViewModel.runState(MainState.CheckModule)
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