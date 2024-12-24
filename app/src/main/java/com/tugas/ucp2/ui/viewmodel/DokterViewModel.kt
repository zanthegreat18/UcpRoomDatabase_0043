package com.tugas.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.ucp2.data.entity.Dokter
import com.tugas.ucp2.data.repositor.RepositoryApp
import kotlinx.coroutines.launch

class DokterViewModel(
    private val repositoryApp: RepositoryApp
) : ViewModel() {

    var uiState by mutableStateOf(DokterUiState())

    fun updateState(DokterEvent: DokterEvent) {
        uiState = uiState.copy(
            dokterEvent = DokterEvent,
        )
    }
    private fun validateField(): Boolean{
        val event = uiState.dokterEvent
        val errorState = FormErrorStateDokter(
            namaError = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            spesialisError = if (event.spesialis.isNotEmpty()) null else "Spesialis tidak boleh kosong",
            klinikError = if (event.klinik.isNotEmpty()) null else "Klinik tidak boleh kosong",
            noHpError = if (event.noHp.isNotEmpty()) null else "No Hp tidak boleh kosong",
            jamKerjaError = if (event.jamKerja.isNotEmpty()) null else "Jam Kerja tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun savedata(){
        val currentEvent = uiState.dokterEvent

        if (validateField()){
            viewModelScope.launch {
                try {
                    repositoryApp.insertDokter(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Disimpan",
                        dokterEvent = DokterEvent(),
                        isEntryValid = FormErrorStateDokter()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else{
            uiState = uiState.copy(
                snackBarMessage = "Inputan Tidak Valid"
            )
        }
    }

    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}


data class DokterUiState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorStateDokter = FormErrorStateDokter(),
    val snackBarMessage: String? = null,
)

data class DokterEvent(
    val id: Int = 0,
    val nama: String = "",
    val spesialis: String = "",
    val klinik: String = "",
    val noHp: String = "",
    val jamKerja: String = ""
)

data class FormErrorStateDokter(
    val namaError: String? = null,
    val spesialisError: String? = null,
    val klinikError: String? = null,
    val noHpError: String? = null,
    val jamKerjaError: String? = null
){
    fun isValid(): Boolean {
        return namaError == null &&
                spesialisError == null &&
                klinikError == null &&
                noHpError == null &&
                jamKerjaError == null
    }
}


fun DokterEvent.toDokterEntity(): Dokter = Dokter(
    id = id,
    nama = nama,
    spesialis = spesialis,
    klinik = klinik,
    noHp = noHp,
    jamKerja = jamKerja
)


