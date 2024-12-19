package com.tugas.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tugas.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow


@Dao
interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter")
    fun getAllDokters(): Flow<List<Dokter>>  // Menggunakan Flow sebagai pengganti LiveData

    // Mengambil data dokter berdasarkan ID
    @Query("SELECT * FROM dokter WHERE id = :dokterId")
    fun getDokterById(dokterId: Int): Flow<Dokter?>  // Menggunakan Flow sebagai pengganti LiveData
}
