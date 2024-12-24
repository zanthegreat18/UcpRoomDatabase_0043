package com.tugas.ucp2.data.injection

import android.content.Context
import com.tugas.ucp2.data.database.AppDatabase
import com.tugas.ucp2.data.repositor.LokalRepositoryApp
import com.tugas.ucp2.data.repositor.RepositoryApp

interface InterfaceContainerApp{
    val repositoryApp: RepositoryApp
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryApp: RepositoryApp by lazy {
        LokalRepositoryApp(AppDatabase.getDatabase(context).dokterDao(),
            AppDatabase.getDatabase(context).jadwalDao())
    }
}










