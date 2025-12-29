package com.example.feature_xml_userlist.presentation.screens.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.feature_xml_userlist.databinding.FragmentBBinding
import com.example.feature_xml_userlist.presentation.screens.MainActivity
import com.example.feature_xml_userlist.presentation.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

class FragmentB : Fragment() {
    private lateinit var binding: FragmentBBinding
    private lateinit var navController: NavController
    private val featureContainer get() = (requireActivity() as MainActivity).featureContainer
    private val viewModelShared by viewModels<SharedViewModel> {
        featureContainer.provideSharedViewModelFactory()
    }
    private val args: FragmentBArgs by navArgs()

    companion object {
        private const val LOGGING_DATA_BUNDLE = "LOGGER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = args.message
        Log.d(LOGGING_DATA_BUNDLE, data)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        observeViewModel()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.decrementButton.setOnClickListener {
            viewModelShared.decrement()
        }
        binding.transitionButtonToAFragment.setOnClickListener {
            val result = "ДРуацвывыаываываыва"
            parentFragmentManager.setFragmentResult(
                "fragmentA_callback_key3455",
                bundleOf("result" to result)
            )
            navController.popBackStack()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelShared.counter.collect { state ->
                    updateUI(state.toString())
                }
            }
        }
    }

    private fun updateUI(state: String) {
        binding.countDec.text = state
    }
}