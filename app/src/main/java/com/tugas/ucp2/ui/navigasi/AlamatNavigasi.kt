package com.tugas.ucp2.ui.navigasi

interface AlamatNavigasi{
    val route: String
}

object Screen : AlamatNavigasi{
    override val route: String = "Screen"
}

object DestinasiHomeDokter : AlamatNavigasi{
    override val route: String = "HomeDokter"
}

object DestinasiHomeJadwal : AlamatNavigasi{
    override val route: String = "HomeJadwal"
}

object DestinasiInsertDokter : AlamatNavigasi{
    override val route: String = "InsertDokter"
}

object DestinasiInsertJadwal : AlamatNavigasi{
    override val route: String = "InsertJadwal"
}

object DestinasiDetail : AlamatNavigasi{
    override val route = "Detail"
    const val IDJL = "idJadwal"
    val routeWithArgs = "$route/{$IDJL}"
}

object DestinasiUpdate : AlamatNavigasi{
    override val route = "Update"
    const val PINJOL = "idJadwal"
    val routeWithArgs = "$route/{$PINJOL}"
}