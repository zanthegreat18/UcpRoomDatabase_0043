package com.tugas.ucp2.data.repositor

import androidx.lifecycle.LiveData
import com.tugas.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoriDktr {
    suspend fun insertDktr(dokter: Dokter)
    suspend fun getDktrById(id: Int): Dokter?
    fun getAllDktr(): LiveData<List<Dokter>>
}
