package com.tugas.ucp2.ui.view.jadwal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tugas.ucp2.data.entity.Jadwal
import com.tugas.ucp2.ui.customwidget.TopAppBar
import com.tugas.ucp2.ui.viewmodel.DetailJadwalViewModel
import com.tugas.ucp2.ui.viewmodel.DetailUiState
import com.tugas.ucp2.ui.viewmodel.PenyediaViewModel
import com.tugas.ucp2.ui.viewmodel.toJadwalEntity


@Composable
fun DetailJdlView(
    modifier: Modifier = Modifier,
    viewModel: DetailJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {}
){
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                judul = "Detail Jadwal",
                showBackButton = true,
                onBack = onBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(viewModel.detailUiState.value.detailUiEvent.idJadwal.toString()) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFF9DBC98)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jadwal"
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(top = 16.dp) // Adds extra space at the top
            ) {
                val detailUiState by viewModel.detailUiState.collectAsState()

                BodyDetailJadwal(
                    modifier = Modifier.fillMaxSize(),
                    detailUiState = detailUiState,
                    onDeleteClick = {
                        viewModel.deleteJadwal()
                        onDeleteClick()
                    }
                )
            }
        }
    )
}

@Composable
fun BodyDetailJadwal(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = {}
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when{
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }

        detailUiState.isUiEventValid -> {
            Column (modifier = modifier.fillMaxWidth().padding(16.dp))
            {
                ItemDetailJadwal(
                    jadwal = detailUiState.detailUiEvent.toJadwalEntity(),
                    modifier = Modifier
                )
                Spacer(modifier =  Modifier.padding(8.dp))
                Button(onClick = {
                    deleteConfirmationRequired = true
                },
                    modifier = Modifier
                    .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9DBC98)
                    )
                )
                {
                    Text(text = "Delete", fontSize = 18.sp)
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmasionDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel =  {
                            deleteConfirmationRequired = false
                        }, modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailJadwal(
    modifier: Modifier = Modifier,
    jadwal: Jadwal
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color(0xFF9DBC98)
        ),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // ID Jadwal dengan ikon
            ComponentDetailJadwal(
                judul = "ID Jadwal",
                isinya = jadwal.idJadwal.toString(),
                icon = Icons.Default.Info,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Nama Pasien dengan ikon
            ComponentDetailJadwal(
                judul = "Nama Pasien",
                isinya = jadwal.namaPasien,
                icon = Icons.Default.Person
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Nama Dokter dengan ikon
            ComponentDetailJadwal(
                judul = "Nama Dokter",
                isinya = jadwal.namaDokter,
                icon = Icons.Default.Person
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // No Hp dengan ikon
            ComponentDetailJadwal(
                judul = "No Hp",
                isinya = jadwal.noHp,
                icon = Icons.Default.Call
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Tanggal Konsultasi dengan ikon
            ComponentDetailJadwal(
                judul = "Tanggal Konsul",
                isinya = jadwal.tanggalKonsultasi,
                icon = Icons.Default.DateRange
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Status dengan ikon
            ComponentDetailJadwal(
                judul = "Status",
                isinya = jadwal.status,
                icon = Icons.Default.CheckCircle
            )
        }
    }
}

@Composable
fun ComponentDetailJadwal(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
    icon: ImageVector? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        // Menambahkan ikon jika ada
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = judul,
                modifier = Modifier
                    .size(35.dp)
                    .padding(end = 12.dp),
                tint = Color(0xFF3E3232)
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = judul,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF3E3232)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = isinya,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3E3232)
            )
        }
    }
}

@Composable
private fun DeleteConfirmasionDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDeleteCancel,
        title = {
            Text(
                "Hapus Data",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(
                "Apakah Anda yakin ingin menghapus data ini? Tindakan ini tidak dapat dibatalkan.",
                fontSize = 16.sp
            )
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        confirmButton = {
            Button(
                onClick = onDeleteConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Hapus", color = Color.White)
            }
        }
    )
}