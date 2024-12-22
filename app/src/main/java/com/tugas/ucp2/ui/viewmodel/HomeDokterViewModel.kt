package com.tugas.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.ucp2.data.entity.Dokter
import com.tugas.ucp2.data.repositor.RepositoryApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDokterViewModel(
    private val repositoryApp: RepositoryApp
): ViewModel() {

    val homeUiState: StateFlow<HomeUiStateDokter> = repositoryApp.getAllDokter()
        .filterNotNull()
        .map {
            HomeUiStateDokter(
                listDokter = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiStateDokter(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiStateDokter(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateDokter(
                isLoading = true,
            )
        )
}



data class HomeUiStateDokter(
    val listDokter: List<Dokter> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)