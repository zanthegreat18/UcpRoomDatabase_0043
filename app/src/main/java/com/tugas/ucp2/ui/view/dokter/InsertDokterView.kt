package com.tugas.ucp2.ui.view.dokter

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
import com.tugas.ucp2.ui.customwidget.TopAppBar
import com.tugas.ucp2.ui.viewmodel.DokterEvent
import com.tugas.ucp2.ui.viewmodel.DokterUiState
import com.tugas.ucp2.ui.viewmodel.DokterViewModel
import com.tugas.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun InsertDokViewPreview() {
    InsertDokView(onBack = {}, onNavigate = {})
}

@Composable
fun InsertDokView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                judul = "Tambah Dokter"
            )
            InsertBodyDokter(
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
fun InsertBodyDokter(
    modifier: Modifier = Modifier,
    onValueChange: (DokterEvent) -> Unit,
    uiState: DokterUiState,
    onClick: () -> Unit = { }
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
            text = "Tambah Dokter",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFF9DBC98),
            modifier = Modifier.padding(bottom = 16.dp)
        )

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
                    text = "Nama",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF9DBC98)
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = "Nama",
                    values = uiState.dokterEvent.nama,
                    error = uiState.isEntryValid.namaError,
                    onValueChange = { onValueChange(uiState.dokterEvent.copy(nama = it)) }
                )
            }
        }

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
                    text = "Spesialis",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF9DBC98)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Selector(
                    selectedSpesialis = uiState.dokterEvent.spesialis,
                    onSelect = { onValueChange(uiState.dokterEvent.copy(spesialis = it)) }
                )
            }
        }

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
                    text = "Jam Kerja",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF9DBC98)
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = "Jam Kerja",
                    values = uiState.dokterEvent.jamKerja,
                    error = uiState.isEntryValid.jamKerjaError,
                    onValueChange = { onValueChange(uiState.dokterEvent.copy(jamKerja = it)) }
                )
            }
        }

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
                    text = "Klinik",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF9DBC98)
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = "Klinik",
                    values = uiState.dokterEvent.klinik,
                    error = uiState.isEntryValid.klinikError,
                    onValueChange = { onValueChange(uiState.dokterEvent.copy(klinik = it)) }
                )
            }
        }

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
                    values = uiState.dokterEvent.noHp,
                    error = uiState.isEntryValid.noHpError,
                    onValueChange = { onValueChange(uiState.dokterEvent.copy(noHp = it)) }
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
    label : String,
    values: String,
    error : String?,
    onValueChange: (String) -> Unit,
){
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = values,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            isError = error != null,
            singleLine = true
        )
        if (error != null){
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Selector(
    selectedSpesialis: String?,
    onSelect: (String) -> Unit
){
    val option = listOf("Bedah Umum", "Jantung", "Penyakit Dalam", "Spesialis Anak", "Gigi")
    val expanded = rememberSaveable { mutableStateOf(false) }
    val currentSelection = remember { mutableStateOf(selectedSpesialis?: option[0]) }

    val MyFocusedContainerColor = Color(0xFF9DBC98)
    val MyUnfocusedContainerColor = Color(0xFF638889)

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ){
        OutlinedTextField(
            value = currentSelection.value,
            onValueChange = {},
            readOnly = true,
            label = { Text("Pilih Spesialis") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MyFocusedContainerColor,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedContainerColor = MyUnfocusedContainerColor,
                unfocusedTextColor = MaterialTheme.colorScheme.surface,
            ),
            shape = RoundedCornerShape(8.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false}
        ) {
            option.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
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
