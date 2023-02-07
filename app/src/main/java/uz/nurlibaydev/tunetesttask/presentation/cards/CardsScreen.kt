package uz.nurlibaydev.tunetesttask.presentation.cards

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.nurlibaydev.tunetesttask.R
import uz.nurlibaydev.tunetesttask.databinding.ScreenCardsBinding
import uz.nurlibaydev.tunetesttask.utils.UiState
import uz.nurlibaydev.tunetesttask.utils.extensions.onClick
import uz.nurlibaydev.tunetesttask.utils.extensions.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 15:41
 */

@AndroidEntryPoint
class CardsScreen : Fragment(R.layout.screen_cards) {

    private val binding: ScreenCardsBinding by viewBinding()
    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: CardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllCardNames()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        binding.apply {
            btnAdd.onClick {
                navController.navigate(CardsScreenDirections.actionCardsScreenToAddCardScreen())
            }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.cardNames.collectLatest {
                when (it) {
                    is UiState.Loading -> {
                        loading(true)
                    }
                    is UiState.NetworkError -> {
                        loading(false)
                        showMessage(it.msg)
                    }
                    is UiState.Error -> {
                        loading(false)
                        showMessage(it.msg)
                    }
                    is UiState.Success -> {
                        loading(false)
                        binding.viewPager.adapter = CardsViewPagerAdapter(requireActivity(), it.data)
                        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
                            tab.text = it.data[pos]
                        }.attach()
                    }
                    else -> {
                        loading(false)
                        showMessage("Unknown error")
                    }
                }
            }
        }
    }

    private fun loading(b: Boolean) {
        binding.progressBar.isVisible = b
    }
}