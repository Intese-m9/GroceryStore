package com.example.feature_xml_userlist.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feature_xml_userlist.databinding.ActivityMainBinding
import com.example.feature_xml_userlist.di.FeatureContainer
import com.example.feature_xml_userlist.presentation.actions.UserEvent
import com.example.feature_xml_userlist.presentation.adapters.UsersAdapter
import com.example.feature_xml_userlist.presentation.screens.fragments.FragmentsFactory
import com.example.feature_xml_userlist.presentation.utils.DragItemTouchCallBack
import com.example.feature_xml_userlist.presentation.utils.UserUIState
import com.example.feature_xml_userlist.presentation.viewmodels.UserViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var itemTouchListener: ItemTouchHelper
    private val viewModelFeature: UserViewModel by viewModels()
    private val usersAdapter = UsersAdapter { user ->
        viewModelFeature.selectedUser(user)
    }
    val featureContainer by lazy {
        FeatureContainer(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = FragmentsFactory(featureContainer)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setUpClickListeners()
        observeViewModel()

    }

    private fun setupRecyclerView() {
        binding.usersRecyclerView.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        usersAdapter.setOnStartDruggable { holder ->
            itemTouchListener.startDrag(holder)
        }
        val callback = DragItemTouchCallBack(
            adapter = usersAdapter,
            onItemMoved = { fromPosition, toPosition ->
                viewModelFeature.movePositionInRecyclerView(fromPosition, toPosition)
            }
        )
        itemTouchListener = ItemTouchHelper(callback)
        itemTouchListener.attachToRecyclerView(binding.usersRecyclerView)
    }


    private fun setUpClickListeners() {
        binding.loadButton.setOnClickListener {
            viewModelFeature.loadUsers()
        }
        binding.closeErrorButton.setOnClickListener {
            viewModelFeature.clearError()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelFeature.uiState.collect { state ->
                    updateUI(state)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelFeature.events.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun updateUI(state: UserUIState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.navHostFragment.visibility = if (state.isLoading) View.GONE else View.VISIBLE

        usersAdapter.submitList(state.users)

        binding.usersRecyclerView.visibility = if (state.isLoading) View.GONE else View.VISIBLE

        if (state.error != null) {
            binding.errorText.text = state.error
            binding.errorCard.visibility = View.VISIBLE
        } else {
            binding.errorCard.visibility = View.GONE
        }

        binding.loadButton.isEnabled = !state.isLoading

    }

    private fun handleEvent(event: UserEvent) {
        when (event) {
            UserEvent.NavigateToDetails -> {
                //Navigation
            }

            is UserEvent.ShowMessage -> {
                Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}