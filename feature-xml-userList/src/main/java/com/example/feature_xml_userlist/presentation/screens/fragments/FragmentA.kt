package com.example.feature_xml_userlist.presentation.screens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.feature_xml_userlist.databinding.FragmentABinding
import com.example.feature_xml_userlist.presentation.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

class FragmentA(
    private val viewModelProvider: ViewModelProvider.Factory
) : Fragment() {
    private lateinit var binding: FragmentABinding
    private lateinit var navController: NavController
    private val viewModelShared by viewModels<SharedViewModel> { viewModelProvider }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        observeViewModel()
        setUpClickListeners()
        parentFragmentManager.setFragmentResultListener(
            "fragmentA_callback_key3455",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            if (requestKey == "fragmentA_callback_key3455") {
                Toast.makeText(
                    requireContext(),
                    bundle.getString("result"), Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun setUpClickListeners() {
        binding.incrementButton.setOnClickListener {
            viewModelShared.increment()
        }

        binding.transitionButtonToBFragment.setOnClickListener {
            val action = FragmentADirections.actionToFragmentB("Hello_Message")
            navController.navigate(action)
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
        binding.countIncrement.text = state
    }

}