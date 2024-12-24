package com.tugas.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.ucp2.data.entity.Jadwal
import com.tugas.ucp2.data.repositor.RepositoryApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeJadwalViewModel(
    private val repositoryApp: RepositoryApp
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiStateJadwal> = repositoryApp.getAllJadwal()
        .filterNotNull()
        .map {
            HomeUiStateJadwal(
                listJadwal = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiStateJadwal(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiStateJadwal(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateJadwal(
                isLoading = true,
            )
        )
}


data class HomeUiStateJadwal(
    val listJadwal: List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""

)