package com.jaehyeon.intinbletestapp.ui.check_module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jaehyeon.intinbletestapp.MainState
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentCheckModuleBinding
import com.jaehyeon.intinbletestapp.util.device.SendMessageType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

/**
 * Created by Jaehyeon on 2022/08/26.
 */
@AndroidEntryPoint
class CheckModuleFragment: Fragment() {

    private lateinit var binding: FragmentCheckModuleBinding
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: CheckModuleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_module, container, false)
        binding.module = activityViewModel.module
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel.receiveChanged.observe(viewLifecycleOwner) {
            if (it == "Recv Data From APP") {
                viewModel.changeStatus(true)
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            binding.status = it
        }

        // 메시지 루틴이 생긴다면 삭제되어야 할 코드.
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            activityViewModel.sendMessage(SendMessageType.Battery)
            delay(2_000)
            activityViewModel.postState(MainState.StandbyModule)
        }
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