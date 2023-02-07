package uz.nurlibaydev.tunetesttask.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import uz.nurlibaydev.tunetesttask.data.models.Card
import uz.nurlibaydev.tunetesttask.data.models.CardDetail
import uz.nurlibaydev.tunetesttask.utils.Constants.CARDS
import uz.nurlibaydev.tunetesttask.utils.UiState
import uz.nurlibaydev.tunetesttask.utils.hasConnection
import java.util.*

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 21:13
 */

class DataHelper(
    private val db: FirebaseFirestore,
) {

    fun getAllCardNames(): Flow<UiState<List<Card>>> = callbackFlow {
        if (hasConnection()) {
            db.collection(CARDS).get()
                .addOnSuccessListener {
                    val cardNames = it.documents.map { doc ->
                        doc.toObject(Card::class.java)!!
                    }
                    trySend(UiState.Success(cardNames))
                }
                .addOnFailureListener {
                    trySend(UiState.Error(it.localizedMessage!!.toString()))
                }
        } else {
            trySend(UiState.NetworkError("No internet connection!"))
        }
        awaitClose { /* unregister listener here */ }
    }.catch {
        emit(UiState.Error(it.toString()))
    }.flowOn(Dispatchers.IO)

    fun getCardList(id: String, cardName: String): Flow<UiState<List<CardDetail>>> = callbackFlow {
        if (hasConnection()) {
            db.collection(CARDS).document(id).collection(cardName).get()
                .addOnSuccessListener {
                    val cardList = it.documents.map { doc ->
                        doc.toObject(CardDetail::class.java)!!
                    }
                    trySend(UiState.Success(cardList))
                }
                .addOnFailureListener {
                    trySend(UiState.Error(it.localizedMessage!!.toString()))
                }
        } else {
            trySend(UiState.NetworkError("No internet connection!"))
        }
        awaitClose { /* unregister listener here */ }
    }.catch {
        emit(UiState.Error(it.toString()))
    }.flowOn(Dispatchers.IO)

    fun addCard(cardName: String, cardNumber: String, date: String): Flow<UiState<String>> = callbackFlow {
        if (hasConnection()) {
            db.collection(CARDS)
                .whereEqualTo("name", cardName)
                .get()
                .addOnSuccessListener {
                    it.documents.map { doc ->
                        val cardDetail = CardDetail(
                            id = UUID.randomUUID().toString(),
                            bank_name = "UZUM BANK",
                            card_name = cardName,
                            card_number = cardNumber,
                            expiry_date = date,
                            isMain = false,
                            owner_name = "NURLIBAY KOSHKINBAEV",
                            total_summa = "0 UZS"
                        )
                        db.collection(CARDS).document(doc.id).collection(cardName).add(cardDetail)
                            .addOnSuccessListener {
                                trySend(UiState.Success("Success. Please refresh page!"))
                            }
                            .addOnFailureListener { e ->
                                trySend(UiState.Error(e.localizedMessage!!.toString()))
                            }
                    }
                }
                .addOnFailureListener {
                    trySend(UiState.Error(it.localizedMessage!!.toString()))
                }
        } else {
            trySend(UiState.NetworkError("No internet connection!"))
        }
        awaitClose { /* unregister listener here */ }
    }.catch {
        emit(UiState.Error(it.toString()))
    }.flowOn(Dispatchers.IO)
}