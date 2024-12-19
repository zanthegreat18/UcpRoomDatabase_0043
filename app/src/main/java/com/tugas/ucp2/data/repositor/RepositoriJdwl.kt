package com.tugas.ucp2.data.repositor

import androidx.lifecycle.LiveData
import com.tugas.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoriJdwl {
    suspend fun insertJadwal(jadwal: Jadwal)
    suspend fun deleteJadwal(jadwal: Jadwal)
    suspend fun updateJadwal(jadwal: Jadwal)
    fun getAllJdwl(): Flow<List<Jadwal>>
    fun getJdwl(jadwalId: Int): Flow<Jadwal?>
}
