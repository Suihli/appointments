package com.example.diseasemonitoring.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diseasemonitoring.models.Appointment
import com.example.diseasemonitoring.viewmodels.AppointmentViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AppointmentScreen(navController: NavController, viewModel: AppointmentViewModel = viewModel()) {
    var isAdding by remember { mutableStateOf(true) }
    var doctorName by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var currentAppointmentId by remember { mutableStateOf<String?>(null) }

    val appointments by viewModel.appointments.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAppointments()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        if (errorMessage != null) {
            Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
        }

        if (isAdding) {
            Text(
                text = if (currentAppointmentId == null) "Add Appointment" else "Edit Appointment",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = doctorName,
                onValueChange = { doctorName = it },
                label = { Text("Doctor Name") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val appointment = Appointment(
                        doctorName = doctorName.text,
                        date = date.text,
                        _id = currentAppointmentId
                    )

                    if (currentAppointmentId == null) {
                        viewModel.addAppointment(appointment)
                    } else {
                        viewModel.updateAppointment(currentAppointmentId!!, appointment)
                    }

                    isAdding = false
                    currentAppointmentId = null
                    doctorName = TextFieldValue("")
                    date = TextFieldValue("")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (currentAppointmentId == null) "Add Appointment" else "Update Appointment")
            }
        } else {
            Text("Appointments List", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(appointments) { appointment ->
                    AppointmentItem(
                        appointment = appointment,
                        onEdit = {
                            doctorName = TextFieldValue(appointment.doctorName)
                            date = TextFieldValue(appointment.date)
                            currentAppointmentId = appointment._id
                            isAdding = true
                        },
                        onDelete = {
                            appointment._id?.let { viewModel.deleteAppointment(it) }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isAdding = true
                    doctorName = TextFieldValue("")
                    date = TextFieldValue("")
                    currentAppointmentId = null
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add New Appointment")
            }
        }
    }
}

@Composable
fun AppointmentItem(
    appointment: Appointment,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = CardDefaults.cardElevation(4.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Doctor: ${appointment.doctorName}", style = MaterialTheme.typography.bodyLarge)
                Text("Date: ${appointment.date}", style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppointmentScreen() {
    val viewModel = AppointmentViewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchAppointments()
    }

    AppointmentScreen(navController = rememberNavController(), viewModel = viewModel)
}
