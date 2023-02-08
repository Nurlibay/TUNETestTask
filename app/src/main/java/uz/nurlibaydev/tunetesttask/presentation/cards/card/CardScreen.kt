package uz.nurlibaydev.tunetesttask.presentation.cards.card

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.nurlibaydev.tunetesttask.R
import uz.nurlibaydev.tunetesttask.databinding.ScreenCardBinding
import uz.nurlibaydev.tunetesttask.utils.UiState
import uz.nurlibaydev.tunetesttask.utils.extensions.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 20:00
 */

@AndroidEntryPoint
open class CardScreen : Fragment(R.layout.screen_card) {

    private val binding: ScreenCardBinding by viewBinding()
    private val adapter by lazy { CardAdapter() }
    private val viewModel: CardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getString("id")
        val cardName = requireArguments().getString("name")
        if (cardName == "All") {
            val cardNames = requireArguments().getStringArrayList("cards")
            viewModel.getAllCardsData(cardNames!!)
            setupObserverAllCardData()
        } else {
            viewModel.getAllCardList(id, cardName)
        }
        binding.rvCards.adapter = adapter
        binding.swipeContainer.setOnRefreshListener {
            if (cardName == "All") {
                val cardNames = requireArguments().getStringArrayList("cards")
                viewModel.getAllCardsData(cardNames!!)
                setupObserverAllCardData()
            } else {
                viewModel.getAllCardList(id, cardName)
            }
            binding.swipeContainer.isRefreshing = false
        }
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.cardList.collectLatest {
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
                        adapter.submitList(it.data)
                    }
                    else -> {
                        loading(false)
                        showMessage("Unknown error")
                    }
                }
            }
        }
    }

    private fun setupObserverAllCardData() {
        lifecycleScope.launch {
            viewModel.allCards.collectLatest {
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
                        adapter.submitList(it.data)
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