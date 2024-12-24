package com.tugas.ucp2

import android.app.Application
import com.tugas.ucp2.data.injection.ContainerApp

class SehatYukApp : Application(){
    lateinit var containerApp: ContainerApp
    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
    }

}