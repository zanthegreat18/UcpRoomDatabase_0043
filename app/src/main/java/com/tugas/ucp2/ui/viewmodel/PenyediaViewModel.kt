package com.tugas.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tugas.ucp2.SehatYukApp

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                SehatYukApp().containerApp.repositoryApp
            )
        }
        initializer {
            HomeDokterViewModel(
                SehatYukApp().containerApp.repositoryApp
            )
        }
        initializer {
            JadwalViewModel(
                SehatYukApp().containerApp.repositoryApp
            )
        }
        initializer {
            HomeJadwalViewModel(
                SehatYukApp().containerApp.repositoryApp
            )
        }
        initializer {
            DetailJadwalViewModel(
                createSavedStateHandle(),
                SehatYukApp().containerApp.repositoryApp
            )
        }
        initializer {
            UpdateJadwalViewModel(
                createSavedStateHandle(),
                SehatYukApp().containerApp.repositoryApp
            )
        }
    }
}

fun CreationExtras.SehatYukApp(): SehatYukApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SehatYukApp)