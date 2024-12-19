package com.tugas.ucp2.data.repositor


import com.tugas.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow


interface RepositoriDktr {
    suspend fun insertDokter(dokter: Dokter)
    fun getAllDktr(): Flow<List<Dokter>>
    fun getDktr(dokterId: Int): Flow<Dokter?>
}
