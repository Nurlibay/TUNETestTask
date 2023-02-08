package uz.nurlibaydev.tunetesttask.presentation.addcard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import butterknife.ButterKnife
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.nurlibaydev.tunetesttask.R
import uz.nurlibaydev.tunetesttask.databinding.ScreenAddCardBinding
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_DATE_DIVIDER
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_DATE_DIVIDER_MODULO
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_DATE_DIVIDER_POSITION
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_DATE_TOTAL_DIGITS
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_DATE_TOTAL_SYMBOLS
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_NUMBER_DIVIDER
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_NUMBER_DIVIDER_MODULO
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_NUMBER_DIVIDER_POSITION
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_NUMBER_TOTAL_DIGITS
import uz.nurlibaydev.tunetesttask.utils.Constants.CARD_NUMBER_TOTAL_SYMBOLS
import uz.nurlibaydev.tunetesttask.utils.UiState
import uz.nurlibaydev.tunetesttask.utils.extensions.onClick
import uz.nurlibaydev.tunetesttask.utils.extensions.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 15:43
 */

@AndroidEntryPoint
class AddCardScreen : Fragment(R.layout.screen_add_card) {

    private val binding: ScreenAddCardBinding by viewBinding()
    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: AddCardViewModel by viewModels()
    private val liveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val validCardNumber: MutableLiveData<Boolean> = MutableLiveData(false)
    private val validCardDate: MutableLiveData<Boolean> = MutableLiveData(false)
    private val cardNumberLiveData: MutableLiveData<String> = MutableLiveData("")
    private val cardDateLiveData: MutableLiveData<String> = MutableLiveData("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnBack.onClick {
                navController.popBackStack()
            }

            editTextCardNumber.addTextChangedListener {
                cardNumberLiveData.value = it.toString()
                cardDateLiveData.observe(viewLifecycleOwner){ cardDate ->
                    if (it.toString().length == 19 && cardDate.length == 5) {
                        validCardNumber.value = true
                        if (validCardNumber.value!! && validCardDate.value!!) {
                            liveData.value = true
                        }
                    } else {
                        liveData.value = false
                    }
                }
            }
            editTextDate.addTextChangedListener {
                cardDateLiveData.value = it.toString()
                cardNumberLiveData.observe(viewLifecycleOwner){ cardNumber ->
                    if (it.toString().length == 5 && cardNumber.length == 19) {
                        validCardDate.value = true
                        if (validCardNumber.value!! && validCardDate.value!!) {
                            liveData.value = true
                        }
                    } else {
                        liveData.value = false
                    }
                }
            }

            liveData.observe(viewLifecycleOwner) {
                btnContinue.isEnabled = it
            }

            btnContinue.onClick {
                var fourDigit = ""
                for (i in 0 until 4) fourDigit += cardNumberLiveData.value!![i]
                if (fourDigit == "9860") {
                    viewModel.addCard("Humo", cardNumberLiveData.value!!, cardDateLiveData.value!!)
                    setupObserver()
                }
                if (fourDigit == "8600") {
                    viewModel.addCard("UzCard", cardNumberLiveData.value!!, cardDateLiveData.value!!)
                    setupObserver()
                } else {
                    viewModel.addCard("VISA", cardNumberLiveData.value!!, cardDateLiveData.value!!)
                    setupObserver()
                }
            }

            editTextCardNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (!isInputCorrect(s, CARD_NUMBER_TOTAL_SYMBOLS, CARD_NUMBER_DIVIDER_MODULO, CARD_NUMBER_DIVIDER)) {
                        s.replace(
                            0,
                            s.length,
                            concatString(getDigitArray(s, CARD_NUMBER_TOTAL_DIGITS), CARD_NUMBER_DIVIDER_POSITION, CARD_NUMBER_DIVIDER)
                        )
                    }
                }
            })

            editTextDate.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (!isInputCorrect(s, CARD_DATE_TOTAL_SYMBOLS, CARD_DATE_DIVIDER_MODULO, CARD_DATE_DIVIDER)) {
                        s.replace(0, s.length, concatString(getDigitArray(s, CARD_DATE_TOTAL_DIGITS), CARD_DATE_DIVIDER_POSITION, CARD_DATE_DIVIDER))
                    }
                }
            })
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.addCard.collectLatest {
                when (it) {
                    is UiState.Loading -> {
                        loading(true)
                    }
                    is UiState.NetworkError -> {
                        loading(false)
                        showMessage(it.msg)
                    }
                    is UiState.Error -> {
                        loading(false)
                        showMessage(it.msg)
                    }
                    is UiState.Success -> {
                        loading(false)
                        showMessage(it.data)
                        navController.popBackStack()
                    }
                    else -> {
                        loading(false)
                        showMessage("Unknown error")
                    }
                }
            }
        }
    }

    private fun loading(b: Boolean) {
        binding.progressBar.isVisible = b
    }

    private fun isInputCorrect(s: Editable, size: Int, dividerPosition: Int, divider: Char): Boolean {
        var isCorrect = s.length <= size
        for (i in s.indices) {
            isCorrect = if (i > 0 && (i + 1) % dividerPosition == 0) {
                isCorrect and (divider == s[i])
            } else {
                isCorrect and Character.isDigit(s[i])
            }
        }
        return isCorrect
    }

    private fun concatString(digits: CharArray, dividerPosition: Int, divider: Char): String? {
        val formatted = StringBuilder()
        for (i in digits.indices) {
            if (digits[i].code != 0) {
                formatted.append(digits[i])
                if ((i > 0) && (i < (digits.size - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider)
                }
            }
        }
        return formatted.toString()
    }

    private fun getDigitArray(s: Editable, size: Int): CharArray {
        val digits = CharArray(size)
        var index = 0
        var i = 0
        while (i < s.length && index < size) {
            val current = s[i]
            if (Character.isDigit(current)) {
                digits[index] = current
                index++
            }
            i++
        }
        return digits
    }
}