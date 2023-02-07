package uz.nurlibaydev.tunetesttask.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import uz.nurlibaydev.tunetesttask.data.models.Card
import uz.nurlibaydev.tunetesttask.utils.Constants
import uz.nurlibaydev.tunetesttask.utils.UiState
import uz.nurlibaydev.tunetesttask.utils.hasConnection

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 21:13
 */

class DataHelper(
    private val db: FirebaseFirestore,
) {

    fun getAllCardNames(): Flow<UiState<List<String>>> = callbackFlow {
        if (hasConnection()) {
            db.collection(Constants.CARDS).get()
                .addOnSuccessListener {
                    val cardNames = it.documents.map { doc ->
                        doc.toObject(Card::class.java)!!
                    }
                    val cards = mutableListOf<String>()
                    cardNames.forEach { card ->
                        cards.add(card.name)
                    }
                    trySend(UiState.Success(cards))
                }
                .addOnFailureListener {
                    trySend(UiState.Error(it.localizedMessage!!.toString()))
                }
        } else {
            trySend(UiState.NetworkError("No internet connection!"))
        }
        awaitClose {}
    }.catch {
        emit(UiState.Error(it.toString()))
    }.flowOn(Dispatchers.IO)
}