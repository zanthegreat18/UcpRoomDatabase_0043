package com.tugas.ucp2.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tugas.ucp2.data.entity.Dokter
import com.tugas.ucp2.data.entity.Jadwal


@Dao
interface JadwalDao {
    @Insert
    suspend fun insertJadwal(jadwal: Jadwal)

    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal)

    @Update
    suspend fun updateJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal")
    fun getAllJadwal(): LiveData<List<Jadwal>>

    // Mengambil data dokter berdasarkan ID
    @Query("SELECT * FROM jadwal WHERE id = :jadwalId")
    suspend fun getJadwalById(jadwalId: Int): Jadwal?
}