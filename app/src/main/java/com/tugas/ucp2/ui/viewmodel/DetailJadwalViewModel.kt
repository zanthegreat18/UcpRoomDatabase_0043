package com.tugas.ucp2.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.ucp2.data.entity.Jadwal
import com.tugas.ucp2.data.repositor.RepositoryApp
import com.tugas.ucp2.ui.navigasi.DestinasiDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryApp: RepositoryApp

): ViewModel(){
    private val _idJadwal: Int = checkNotNull(savedStateHandle[DestinasiDetail.IDJL])

    val detailUiState: StateFlow<DetailUiState> = repositoryApp.getJadwalById(_idJadwal)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,),

        )

    fun deleteJadwal(){
        detailUiState.value.detailUiEvent.toJadwalEntity().let {
            viewModelScope.launch {
                repositoryApp.deleteJadwal(it)
            }
        }
    }
}


data class DetailUiState(
    val detailUiEvent: JadwalEvent = JadwalEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == JadwalEvent()

    val isUiEventValid: Boolean
        get() = detailUiEvent != JadwalEvent()
}



fun Jadwal.toDetailUiEvent() : JadwalEvent{
    return JadwalEvent(
        idJadwal = idJadwal,
        namaDokter = namaDokter,
        namaPasien = namaPasien,
        noHp = noHp,
        tanggalKonsultasi = tanggalKonsultasi,
        status = status
    )
}