package com.tugas.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.ucp2.data.entity.Dokter
import com.tugas.ucp2.data.entity.Jadwal
import com.tugas.ucp2.data.repositor.RepositoryApp
import kotlinx.coroutines.launch

class JadwalViewModel(
    private val repositoryApp: RepositoryApp
): ViewModel() {

    var uiState by mutableStateOf(JadwalUiState())

    init {
        viewModelScope.launch {
            try {
                repositoryApp.getAllDokter().collect { dokterList ->
                    uiState = uiState.copy(dokterList = dokterList)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(snackBarMessage = "Gagal memuat data dokter")
            }
        }
    }

    fun updateState(JadwalEvent: JadwalEvent) {
        uiState = uiState.copy(
            jadwalEvent = JadwalEvent,
        )
    }

    private fun validateField(): Boolean{
        val event = uiState.jadwalEvent
        val errorState = FormErrorStateJadwal(
            namaDokterError = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            namaPasienError = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHpError = if (event.noHp.isNotEmpty()) null else "No Hp tidak boleh kosong",
            tanggalKonsultasiError = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong",
            statusError = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun savedata(){
        val currentEvent = uiState.jadwalEvent

        if (validateField()){
            viewModelScope.launch {
                try {
                    repositoryApp.insertJadwal(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Disimpan",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJadwal()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan",
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Inputan Tidak Valid"
            )

        }
    }
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}


data class JadwalEvent(
    val idJadwal: Int = 0,
    val namaDokter: String = "",
    val namaPasien: String = "",
    val noHp: String = "",
    val tanggalKonsultasi: String = "",
    val status: String = ""
)

fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    idJadwal = idJadwal,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noHp = noHp,
    tanggalKonsultasi = tanggalKonsultasi,
    status = status
)

data class JadwalUiState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorStateJadwal = FormErrorStateJadwal(),
    val snackBarMessage: String? = null,
    val dokterList: List<Dokter> = emptyList()
)

data class FormErrorStateJadwal(
    val namaDokterError: String? = null,
    val namaPasienError: String? = null,
    val noHpError: String? = null,
    val tanggalKonsultasiError: String? = null,
    val statusError: String? = null
){
    fun isValid(): Boolean {
        return namaDokterError == null &&
                namaPasienError == null &&
                noHpError == null &&
                tanggalKonsultasiError == null &&
                statusError == null
    }
}

