package uz.nurlibaydev.tunetesttask.data.models

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 21:15
 */

data class CardDetail(
    val id: String = "",
    val bank_name: String = "",
    val card_name: String = "",
    val card_number: String = "",
    val expiry_date: String = "",
    var isMain: Boolean = false,
    val owner_name: String = "",
    val total_summa: String = "500 000 UZS",
)