package uz.nurlibaydev.tunetesttask.presentation.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.nurlibaydev.tunetesttask.R
import uz.nurlibaydev.tunetesttask.databinding.ScreenCardsBinding

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 15:41
 */

@AndroidEntryPoint
class CardsScreen : Fragment(R.layout.screen_cards) {

    private val binding: ScreenCardsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}