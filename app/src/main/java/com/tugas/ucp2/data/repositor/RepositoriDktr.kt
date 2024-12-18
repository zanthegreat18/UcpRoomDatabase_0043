package com.tugas.ucp2.data.repositor

import com.tugas.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoriDktr {
    suspend fun insertDktr (dokter: Dokter)
    suspend fun getDktrById(id: Int): Dokter?
    fun getAllDktr(): Flow<List<Dokter>>
    fun getDokterById(dokterId: Int): Dokter?
}