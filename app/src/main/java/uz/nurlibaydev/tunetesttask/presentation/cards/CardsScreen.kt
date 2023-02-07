package uz.nurlibaydev.tunetesttask.presentation.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.nurlibaydev.tunetesttask.R
import uz.nurlibaydev.tunetesttask.databinding.ScreenCardsBinding
import uz.nurlibaydev.tunetesttask.utils.extensions.onClick

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 15:41
 */

@AndroidEntryPoint
class CardsScreen : Fragment(R.layout.screen_cards) {

    private val binding: ScreenCardsBinding by viewBinding()
    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnAdd.onClick {
                navController.navigate(CardsScreenDirections.actionCardsScreenToAddCardScreen())
            }
        }
    }
}