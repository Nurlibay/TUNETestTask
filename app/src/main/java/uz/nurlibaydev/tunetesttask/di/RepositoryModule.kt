package uz.nurlibaydev.tunetesttask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.nurlibaydev.tunetesttask.data.DataHelper
import uz.nurlibaydev.tunetesttask.domain.MainRepository
import uz.nurlibaydev.tunetesttask.domain.MainRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @[Provides Singleton]
    fun provideFirebaseRepository(
        dataHelper: DataHelper,
    ): MainRepository {
        return MainRepositoryImpl(dataHelper)
    }

}