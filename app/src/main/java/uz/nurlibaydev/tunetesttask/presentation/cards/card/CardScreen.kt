package uz.nurlibaydev.tunetesttask.presentation.cards.card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.nurlibaydev.tunetesttask.R
import uz.nurlibaydev.tunetesttask.databinding.ScreenCardBinding

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 20:00
 */

@AndroidEntryPoint
class CardScreen : Fragment(R.layout.screen_card) {

    private val binding: ScreenCardBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }
}