package uz.nurlibaydev.tunetesttask.domain

import kotlinx.coroutines.flow.Flow
import uz.nurlibaydev.tunetesttask.data.models.Card
import uz.nurlibaydev.tunetesttask.utils.UiState

interface MainRepository {

    fun getAllCardName(): Flow<UiState<List<Card>>>
}