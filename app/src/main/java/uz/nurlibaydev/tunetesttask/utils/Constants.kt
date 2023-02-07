package uz.nurlibaydev.tunetesttask.utils

object Constants {

    const val CARDS = "cards"

    const val CARD_NUMBER_TOTAL_SYMBOLS = 19 // size of pattern 0000-0000-0000-0000

    const val CARD_NUMBER_TOTAL_DIGITS = 16 // max numbers of digits in pattern: 0000 x 4

    const val CARD_NUMBER_DIVIDER_MODULO = 5 // means divider position is every 5th symbol beginning with 1

    const val CARD_NUMBER_DIVIDER_POSITION = CARD_NUMBER_DIVIDER_MODULO - 1 // means divider position is every 4th symbol beginning with 0

    const val CARD_NUMBER_DIVIDER = ' '

    const val CARD_DATE_TOTAL_SYMBOLS = 5 // size of pattern MM/YY

    const val CARD_DATE_TOTAL_DIGITS = 4 // max numbers of digits in pattern: MM + YY

    const val CARD_DATE_DIVIDER_MODULO = 3 // means divider position is every 3rd symbol beginning with 1

    const val CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1 // means divider position is every 2nd symbol beginning with 0

    const val CARD_DATE_DIVIDER = '/'
}