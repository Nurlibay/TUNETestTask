package uz.nurlibaydev.tunetesttask.presentation.cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.nurlibaydev.tunetesttask.presentation.cards.card.CardScreen

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 19:29
 */

class CardsViewPagerAdapter(
    fa: FragmentActivity,
    private val cards: List<String>
): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = cards.size

    override fun createFragment(position: Int): Fragment {
        val cardsScreen = CardScreen()
        val args = Bundle()
        args.putString("title", cards[position])
        cardsScreen.arguments = args
        return cardsScreen
    }
}