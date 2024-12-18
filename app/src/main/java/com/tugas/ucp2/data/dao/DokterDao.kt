package com.tugas.ucp2.data.dao

import androidx.room.Insert
import androidx.room.Query
import com.tugas.ucp2.data.entity.Dokter

interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter ORDER BY nama ASC")
    fun getAllDokter(): List<Dokter>


}