package com.tugas.ucp2.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tugas.ucp2.ui.view.Screen.Screen1
import com.tugas.ucp2.ui.view.dokter.HomeDokterView
import com.tugas.ucp2.ui.view.dokter.InsertDokView
import com.tugas.ucp2.ui.view.jadwal.DetailJdlView
import com.tugas.ucp2.ui.view.jadwal.HomeJadwalView
import com.tugas.ucp2.ui.view.jadwal.InsertJdlView
import com.tugas.ucp2.ui.view.jadwal.UpdateJdlView


@Composable
fun PengelolaHalamanHome(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Screen.route
    ) {
        composable(
            route = Screen.route
        ){
            Screen1(
                onNavigate = {
                    navController.navigate(DestinasiHomeDokter.route)
                }
            )
        }

        composable(
            route = DestinasiHomeDokter.route
        ) {
            HomeDokterView(
                onAddDokter = {
                    navController.navigate(DestinasiInsertDokter.route)
                },
                onSeeJadwal = {
                    navController.navigate(DestinasiHomeJadwal.route)
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsertDokter.route
        ) {
            InsertDokView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiHomeJadwal.route
        ) {
            HomeJadwalView(
                onAddJdl = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                onSeeDok = {
                    navController.navigate(DestinasiHomeDokter.route)
                },
                onDetailClick = { idJdl ->
                    navController.navigate("${DestinasiDetail.route}/$idJdl")
                    println("PengelolaHalaman: idJdl = $idJdl")
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsertJadwal.route
        ) {
            InsertJdlView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.IDJL){
                    type = NavType.IntType
                }
            )
        ){
            val noJdl = it.arguments?.getInt(DestinasiDetail.IDJL)
            noJdl?.let { noJdl ->
                DetailJdlView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdate.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdate.PINJOL){
                    type = NavType.IntType
                }
            )
        ){
            UpdateJdlView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}

