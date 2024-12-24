package com.tugas.ucp2.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tugas.ucp2.data.entity.Dokter
import com.tugas.ucp2.ui.customwidget.TopAppBar
import com.tugas.ucp2.ui.viewmodel.JadwalEvent
import com.tugas.ucp2.ui.viewmodel.JadwalUiState
import com.tugas.ucp2.ui.viewmodel.JadwalViewModel
import com.tugas.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun InsertJdlViewPreview() {
    InsertJdlView(onBack = {}, onNavigate = {})
}

@Composable
fun InsertJdlView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Jadwal"
            )
            InsertBodyJadwal(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.savedata()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyJadwal(
    modifier: Modifier = Modifier,
    onValueChange: (JadwalEvent) -> Unit,
    uiState: JadwalUiState,
    onClick: () -> Unit,
    dokterList: List<Dokter> = uiState.dokterList
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tambah Jadwal",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF9DBC98),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Form Input Nama Pasien
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 6.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Nama Pasien",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF9DBC98)
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = "Nama Pasien",
                    value = uiState.jadwalEvent.namaPasien,
                    error = uiState.isEntryValid.namaPasienError,
                    onValueChange = { onValueChange(uiState.jadwalEvent.copy(namaPasien = it)) }
                )
            }
        }

        // Form Input Nama Dokter
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 6.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Dokter",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF9DBC98)
                )
                Spacer(modifier = Modifier.height(8.dp))
                SelectorJadwal(
                    selected = uiState.jadwalEvent.namaDokter,
                    onSelect = { selectedDokter ->
                        onValueChange(uiState.jadwalEvent.copy(namaDokter = selectedDokter))
                    },
                    dokterList = dokterList
                )
            }
        }

        // Form Input No Hp
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 6.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "No Hp",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF9DBC98)
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = "No Hp",
                    value = uiState.jadwalEvent.noHp,
                    error = uiState.isEntryValid.noHpError,
                    onValueChange = { onValueChange(uiState.jadwalEvent.copy(noHp = it)) }
                )
            }
        }

        // Form Input Tanggal Konsul
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 6.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tanggal Konsul",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF9DBC98)
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = "Tanggal Konsul",
                    value = uiState.jadwalEvent.tanggalKonsultasi,
                    error = uiState.isEntryValid.tanggalKonsultasiError,
                    onValueChange = { onValueChange(uiState.jadwalEvent.copy(tanggalKonsultasi = it)) }
                )
            }
        }

        // Form Input Status
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 6.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Status",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF9DBC98)
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = "Status",
                    value = uiState.jadwalEvent.status,
                    error = uiState.isEntryValid.statusError,
                    onValueChange = { onValueChange(uiState.jadwalEvent.copy(status = it)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .size(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9DBC98), // Ganti dengan warna latar belakang yang Anda inginkan
                contentColor = Color.White // Ganti dengan warna teks yang Anda inginkan
            ),
        ) {
            Text("Simpan", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = error != null,
            singleLine = true
        )
        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorJadwal(
    selected: String?,
    onSelect: (String) -> Unit,
    dokterList: List<Dokter>
) {
    val options = dokterList.map { it.nama }
    val expanded = rememberSaveable { mutableStateOf(false) }
    val currentSelection = remember { mutableStateOf(selected ?: options.getOrNull(0)) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = currentSelection.value ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Nama Dokter") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(8.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        currentSelection.value = selectionOption
                        expanded.value = false
                        onSelect(selectionOption)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}