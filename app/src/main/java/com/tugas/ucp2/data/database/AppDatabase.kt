package com.tugas.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tugas.ucp2.data.dao.DokterDao
import com.tugas.ucp2.data.dao.JadwalDao
import com.tugas.ucp2.data.entity.Dokter
import com.tugas.ucp2.data.entity.Jadwal


@Database(entities = [Dokter::class, Jadwal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Deklarasi DAO untuk Dokter
    abstract fun dokterDao(): DokterDao

    // Deklarasi DAO untuk Jadwal
    abstract fun jadwalDao(): JadwalDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, //class database
                    "KrsDatabase" //nama database
                )
                    .build().also { Instance = it }
            })
        }
    }
}


