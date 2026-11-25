
package com.example.loopit.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loopit.data.local.LoopitDatabase
import com.example.loopit.data.repository.RewardsRepositoryImpl

class RewardsViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    // Get the Room database instance
    private val database =
        LoopitDatabase.getDatabase(context.applicationContext)

    // Create the rewards repository
    private val repository = RewardsRepositoryImpl(database)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        // Create the RewardsViewModel with its repository
        if (modelClass.isAssignableFrom(RewardsViewModel::class.java)) {
            return RewardsViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
