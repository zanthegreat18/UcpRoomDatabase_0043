package com.tugas.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.ucp2.data.entity.Jadwal
import com.tugas.ucp2.data.repositor.RepositoryApp
import com.tugas.ucp2.ui.navigasi.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryApp: RepositoryApp
): ViewModel() {
    var updateUiState by mutableStateOf(JadwalUiState())
        private set
    private val _idJadwal: Int = checkNotNull(savedStateHandle[DestinasiUpdate.PINJOL])

    init {
        viewModelScope.launch {
            updateUiState = repositoryApp.getJadwalById(_idJadwal)
                .filterNotNull()
                .first()
                .toJadwalUiState()
        }
    }
    fun updateState(JadwalEvent: JadwalEvent) {
        updateUiState = updateUiState.copy(
            jadwalEvent = JadwalEvent,
        )
    }
    fun validateField(): Boolean{
        val event = updateUiState.jadwalEvent
        val errorState = FormErrorStateJadwal(
            namaDokterError = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            namaPasienError = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHpError = if (event.noHp.isNotEmpty()) null else "No Hp tidak boleh kosong",
            tanggalKonsultasiError = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong",
            statusError = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong"
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun updateData(){
        val currentEvent = updateUiState.jadwalEvent

        if (validateField()){
            viewModelScope.launch {
                try {
                    repositoryApp.updateJadwal(currentEvent.toJadwalEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Disimpan",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJadwal()
                    )
                    println()
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else{
            updateUiState =updateUiState.copy(
                snackBarMessage = "Inputan Tidak Valid"
            )
        }
    }
    fun resetSnackBarMessage(){
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
}

fun Jadwal.toJadwalUiState(): JadwalUiState = JadwalUiState(
    jadwalEvent = this.toDetailUiEvent(),
)





