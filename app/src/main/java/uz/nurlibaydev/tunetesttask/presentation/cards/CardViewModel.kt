package uz.nurlibaydev.tunetesttask.presentation.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.nurlibaydev.tunetesttask.data.models.Card
import uz.nurlibaydev.tunetesttask.domain.MainRepository
import uz.nurlibaydev.tunetesttask.utils.UiState
import uz.nurlibaydev.tunetesttask.utils.hasConnection
import javax.inject.Inject

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 21:51
 */

@HiltViewModel
class CardViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _cardNames = MutableStateFlow<UiState<List<String>>>(UiState.Empty)
    val cardNames: StateFlow<UiState<List<String>>> = _cardNames

    fun getAllCardNames() {
        if (!hasConnection()) {
            _cardNames.value = UiState.NetworkError("No Internet Connection!")
            return
        }
        viewModelScope.launch {
            _cardNames.value = UiState.Loading
            mainRepository.getAllCardName().collect {
                when (it) {
                    is UiState.Success -> {
                        val result = it.data
                        _cardNames.value = UiState.Success(result)
                    }
                    is UiState.Error -> {
                        _cardNames.value = UiState.Error(it.msg)
                    }
                    else -> {
                        _cardNames.value = UiState.Error("Unknown Error")
                    }
                }
            }
        }
    }
}