package uz.nurlibaydev.tunetesttask.domain

import kotlinx.coroutines.flow.Flow
import uz.nurlibaydev.tunetesttask.data.DataHelper
import uz.nurlibaydev.tunetesttask.data.models.Card
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
}