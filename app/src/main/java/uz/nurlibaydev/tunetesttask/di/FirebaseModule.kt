package uz.nurlibaydev.tunetesttask.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.nurlibaydev.tunetesttask.data.DataHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @[Provides Singleton]
    fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @[Provides Singleton]
    fun provideDataHelper(db: FirebaseFirestore) = DataHelper(db)
}