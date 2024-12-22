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

    @Update
    suspend fun updateJadwal(jadwal: Jadwal)

    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal ORDER BY namaPasien ASC")
    fun getAllJadwal(): Flow<List<Jadwal>>

    @Query("SELECT * FROM jadwal WHERE idJadwal = :idJadwal")
    fun getJadwalById(idJadwal: Int): Flow<Jadwal>


}