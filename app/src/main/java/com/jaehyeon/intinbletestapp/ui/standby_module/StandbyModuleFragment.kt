package com.jaehyeon.intinbletestapp.ui.standby_module

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jaehyeon.intinbletestapp.MainState
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentStandbyModuleBinding
import com.jaehyeon.intinbletestapp.util.device.SendMessageType
import com.jaehyeon.intinbletestapp.util.event.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Jaehyeon on 2022/08/26.
 */
@AndroidEntryPoint
class StandbyModuleFragment: Fragment() {

    private lateinit var binding: FragmentStandbyModuleBinding
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: StandbyViewModel by viewModels()
    private var finishFlag = false

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
        binding.avm = activityViewModel
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.state.collectLatest {
                when(it) {
                    StandbyState.Start -> {
                        binding.countStartBtn.background = ContextCompat.getDrawable(requireContext(), R.mipmap.a2403)
                    }
                    StandbyState.Finish -> {
                        activityViewModel.sendMessage(SendMessageType.ReqSpiData)
                        activityViewModel.runState(MainState.Result)
                    }
                    else -> {
                        binding.countStartBtn.background = ContextCompat.getDrawable(requireContext(), R.mipmap.a2404)
                    }
                }
            }
        }

        viewModel.action.observe(viewLifecycleOwner, EventObserver {
            when(it) {
                ButtonState.OnClickResumeButton -> {
                    activityViewModel.sendMessage(SendMessageType.Pause)
                }
                ButtonState.OnClickStartButton -> {
                    activityViewModel.sendMessage(SendMessageType.Start)
                }
                ButtonState.OnLongClickButton -> {
                    finishFlag = true
                    activityViewModel.sendMessage(SendMessageType.Stop)
                }
            }
        })

        activityViewModel.receiveChanged.observe(viewLifecycleOwner) {
            Log.e(javaClass.simpleName, "onViewCreated: $it", )
            if (it == "Recv Data From APP") {
                if (!finishFlag)
                    viewModel.changeState()
                else
                    viewModel.finishTest()
            }
        }
    }

}