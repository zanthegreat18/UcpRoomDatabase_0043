package com.tugas.ucp2.data.repositor

import com.tugas.ucp2.data.dao.DokterDao
import com.tugas.ucp2.data.dao.JadwalDao
import com.tugas.ucp2.data.entity.Dokter
import com.tugas.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

class LokalRepositoryApp(
    private val dokterDao: DokterDao,
    private val jadwalDao: JadwalDao
) : RepositoryApp {
    override suspend fun insertDokter(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }
    override fun getAllDokter(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }
    override suspend fun insertJadwal(jadwal: Jadwal) {
        jadwalDao.insertJadwal(jadwal)
    }
    override suspend fun updateJadwal(jadwal: Jadwal) {
        jadwalDao.updateJadwal(jadwal)
    }
    override suspend fun deleteJadwal(jadwal: Jadwal) {
        jadwalDao.deleteJadwal(jadwal)
    }
    override fun getAllJadwal(): Flow<List<Jadwal>> {
        return jadwalDao.getAllJadwal()
    }
    override fun getJadwalById(idJadwal: Int): Flow<Jadwal> {
        return jadwalDao.getJadwalById(idJadwal)
    }
}
