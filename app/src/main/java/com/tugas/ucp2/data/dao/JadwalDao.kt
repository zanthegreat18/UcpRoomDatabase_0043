package com.tugas.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tugas.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow


@Dao
interface JadwalDao {
    @Insert
    suspend fun insertJadwal(jadwal: Jadwal)

    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal)

    @Update
    suspend fun updateJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal")
    fun getAllJadwal(): Flow<List<Jadwal>>  // Menggunakan Flow sebagai pengganti LiveData

    // Mengambil data jadwal berdasarkan ID
    @Query("SELECT * FROM jadwal WHERE id = :jadwalId")
    fun getJadwalById(jadwalId: Int): Flow<Jadwal?>  // Menggunakan Flow sebagai pengganti LiveData
}
