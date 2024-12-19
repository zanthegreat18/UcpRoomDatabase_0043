package com.tugas.ucp2.data.repositor

import androidx.lifecycle.LiveData
import com.tugas.ucp2.data.dao.DokterDao
import com.tugas.ucp2.data.entity.Dokter

class LokalRepositoriDktr(private val dokterDao: DokterDao) : RepositoriDktr {

    // Implementasi fungsi untuk menyisipkan dokter
    override suspend fun insertDktr(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }

    // Implementasi fungsi untuk mendapatkan dokter berdasarkan ID
    override suspend fun getDktrById(id: Int): Dokter? {
        return dokterDao.getDokterById(id)
    }

    // Implementasi fungsi untuk mendapatkan semua dokter sebagai LiveData
    override fun getAllDktr(): LiveData<List<Dokter>> {
        return dokterDao.getAllDokters()
    }
}

