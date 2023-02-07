package uz.nurlibaydev.tunetesttask.presentation.cards.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.nurlibaydev.tunetesttask.data.models.CardDetail
import uz.nurlibaydev.tunetesttask.domain.MainRepository
import uz.nurlibaydev.tunetesttask.utils.UiState
import uz.nurlibaydev.tunetesttask.utils.hasConnection
import javax.inject.Inject

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 23:51
 */

@HiltViewModel
class CardViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _cardList = MutableStateFlow<UiState<List<CardDetail>>>(UiState.Empty)
    val cardList: StateFlow<UiState<List<CardDetail>>> = _cardList

    fun getAllCardList(id: String? = "", name: String? = "") {
        if (!hasConnection()) {
            _cardList.value = UiState.NetworkError("No Internet Connection!")
            return
        }
        viewModelScope.launch {
            _cardList.value = UiState.Loading
            mainRepository.getCardList(id!!, name!!).collect {
                when (it) {
                    is UiState.Success -> {
                        val result = it.data
                        _cardList.value = UiState.Success(result)
                    }
                    is UiState.Error -> {
                        _cardList.value = UiState.Error(it.msg)
                    }
                    else -> {
                        _cardList.value = UiState.Error("Unknown Error")
                    }
                }
            }
        }
    }
}