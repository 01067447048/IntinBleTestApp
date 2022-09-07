package com.jaehyeon.intinbletestapp.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jaehyeon.intinbletestapp.MainState
import com.jaehyeon.intinbletestapp.MainViewModel
import com.jaehyeon.intinbletestapp.R
import com.jaehyeon.intinbletestapp.databinding.FragmentResultBinding
import com.jaehyeon.intinbletestapp.util.button.ButtonState
import com.jaehyeon.intinbletestapp.util.button.ButtonViewModel
import com.jaehyeon.intinbletestapp.util.event.EventObserver
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jaehyeon on 2022/08/26.
 */
@AndroidEntryPoint
class ResultFragment: Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val activityViewModel: MainViewModel by activityViewModels()
    private val buttonViewModel: ButtonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        binding.includeButton.btnVm = buttonViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonViewModel.setLoginButtonText("다시 검사")
        buttonViewModel.setButtonEnable(true)

        buttonViewModel.buttonState.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                ButtonState.OnClick -> activityViewModel.runState(MainState.ChoiceDevice)
            }
        })
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