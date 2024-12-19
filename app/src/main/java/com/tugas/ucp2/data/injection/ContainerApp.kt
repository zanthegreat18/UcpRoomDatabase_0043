package com.tugas.ucp2.data.injection

import com.tugas.ucp2.data.repositor.RepositoriDktr

interface ContainerApp {
    val repositoriDokter: RepositoriDktr
}