package com.example.loopit.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loopit.data.local.LoopitDatabase
import com.example.loopit.data.repository.CollectionPointsRepositoryImpl
import com.example.loopit.domain.usecase.GetCollectionPointsUseCase
import com.example.loopit.domain.usecase.GetCollectionPointDetailsUseCase

class CollectionPointViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    private val database =
        LoopitDatabase.getDatabase(context.applicationContext)

    private val repository = CollectionPointsRepositoryImpl(
        collectionPointDao = database.collectionPointDao(),
        collectionPointDeviceDao = database.collectionPointDeviceDao()
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                CollectionPointViewModel::class.java
            )
        ) {
            return CollectionPointViewModel(
                getCollectionPointsUseCase =
                    GetCollectionPointsUseCase(repository),
                getCollectionPointDetailsUseCase =
                    GetCollectionPointDetailsUseCase(repository)
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}