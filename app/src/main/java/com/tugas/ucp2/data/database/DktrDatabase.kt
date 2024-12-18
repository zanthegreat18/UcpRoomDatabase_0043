package com.tugas.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tugas.ucp2.data.dao.DokterDao
import com.tugas.ucp2.data.entity.Dokter


@Database(entities = [Dokter::class], version = 1, exportSchema = false)
abstract class DktrDatabase : RoomDatabase() {

    abstract fun DokterDao(): DokterDao

    companion object {
        @Volatile
        private var Instance: DktrDatabase? = null

        fun getDatabase(context: Context): DktrDatabase {
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    DktrDatabase::class.java, //class database
                    "KrsDatabase" //nama database
                )
                    .build().also { Instance = it }
            })
        }
    }
}


