package com.tugas.ucp2.data.repositor

import com.tugas.ucp2.data.entity.Dokter
import com.tugas.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryApp {
    suspend fun insertJadwal (jadwal: Jadwal)
    suspend fun updateJadwal (jadwal: Jadwal)
    suspend fun deleteJadwal (jadwal: Jadwal)
    fun getAllJadwal(): Flow<List<Jadwal>>
    fun getJadwalById(idJadwal: Int): Flow<Jadwal>

    suspend fun insertDokter (dokter: Dokter)
    fun getAllDokter(): Flow<List<Dokter>>
}