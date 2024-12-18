package com.tugas.ucp2.data.database

import androidx.room.RoomDatabase
import com.tugas.ucp2.data.dao.JadwalDao

abstract class JdwlDatabase : RoomDatabase(){

    abstract fun jadwalDao(): JadwalDao

    companion object
    @Volatile
    private var Instance: JdwlDatabase? = null
}
