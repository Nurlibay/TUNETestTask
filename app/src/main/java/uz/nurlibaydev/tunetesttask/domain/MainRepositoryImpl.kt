package uz.nurlibaydev.tunetesttask.domain

import kotlinx.coroutines.flow.Flow
import uz.nurlibaydev.tunetesttask.data.DataHelper
import uz.nurlibaydev.tunetesttask.data.models.Card
import uz.nurlibaydev.tunetesttask.data.models.CardDetail
import uz.nurlibaydev.tunetesttask.utils.UiState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper
) : MainRepository {

    override fun getAllCardName(): Flow<UiState<List<Card>>> {
        return dataHelper.getAllCardNames()
    }

    override fun getCardList(id: String, name: String): Flow<UiState<List<CardDetail>>> {
        return dataHelper.getCardList(id, name)
    }

    override fun addCard(name: String, cardNumber: String, date: String): Flow<UiState<String>> {
        return dataHelper.addCard(name, cardNumber, date)
    }
}