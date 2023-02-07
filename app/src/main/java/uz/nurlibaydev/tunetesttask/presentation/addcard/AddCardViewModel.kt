package uz.nurlibaydev.tunetesttask.presentation.addcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.nurlibaydev.tunetesttask.domain.MainRepository
import uz.nurlibaydev.tunetesttask.utils.UiState
import uz.nurlibaydev.tunetesttask.utils.hasConnection
import javax.inject.Inject

/**
 *  Created by Nurlibay Koshkinbaev on 08/02/2023 04:10
 */

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _addCard = MutableStateFlow<UiState<String>>(UiState.Empty)
    val addCard: StateFlow<UiState<String>> = _addCard

    fun addCard(name: String, cardNumber: String, date: String) {
        if (!hasConnection()) {
            _addCard.value = UiState.NetworkError("No Internet Connection!")
            return
        }
        viewModelScope.launch {
            _addCard.value = UiState.Loading
            mainRepository.addCard(name, cardNumber, date).collect {
                when (it) {
                    is UiState.Success -> {
                        val result = it.data
                        _addCard.value = UiState.Success(result)
                    }
                    is UiState.Error -> {
                        _addCard.value = UiState.Error(it.msg)
                    }
                    else -> {
                        _addCard.value = UiState.Error("Unknown Error")
                    }
                }
            }
        }
    }
}