package com.tugas.ucp2.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tugas.ucp2.data.entity.Dokter


@Dao
interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter")
    fun getAllDokters(): LiveData<List<Dokter>>

    // Mengambil data dokter berdasarkan ID
    @Query("SELECT * FROM dokter WHERE id = :dokterId")
    suspend fun getDokterById(dokterId: Int): Dokter?
}