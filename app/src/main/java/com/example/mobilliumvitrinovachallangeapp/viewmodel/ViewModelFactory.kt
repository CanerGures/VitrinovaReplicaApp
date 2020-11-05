package com.example.mobilliumvitrinovachallangeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobilliumvitrinovachallangeapp.api.repository.ContentRepository

class ViewModelFactory(private val repository: ContentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ContentViewModel::class.java) -> {
                ContentViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Not Assignable Class")
            }
        }
    }
}