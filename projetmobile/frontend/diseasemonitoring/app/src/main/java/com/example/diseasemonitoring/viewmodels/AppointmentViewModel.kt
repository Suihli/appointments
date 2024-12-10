package com.example.diseasemonitoring.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diseasemonitoring.models.Appointment
import com.example.diseasemonitoring.network.RetrofitInstance
import kotlinx.coroutines.launch

class AppointmentViewModel : ViewModel() {

    val appointments = MutableLiveData<List<Appointment>>()
    val errorMessage = MutableLiveData<String>()

    // Récupérer les rendez-vous
    fun fetchAppointments() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAppointments()
                if (response.isSuccessful) {
                    appointments.postValue(response.body())
                } else {
                    errorMessage.postValue("Error fetching appointments: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Network error: ${e.message}")
            }
        }
    }

    // Ajouter un rendez-vous
    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.createAppointment(appointment)
                if (response.isSuccessful) {
                    fetchAppointments()
                } else {
                    errorMessage.postValue("Error adding appointment")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Network error: ${e.message}")
            }
        }
    }

    // Mettre à jour un rendez-vous
    fun updateAppointment(id: String, appointment: Appointment) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateAppointment(id, appointment)
                if (response.isSuccessful) {
                    fetchAppointments()
                } else {
                    errorMessage.postValue("Error updating appointment")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Network error: ${e.message}")
            }
        }
    }

    // Supprimer un rendez-vous
    fun deleteAppointment(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteAppointment(id)
                if (response.isSuccessful) {
                    fetchAppointments()
                } else {
                    errorMessage.postValue("Error deleting appointment")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Network error: ${e.message}")
            }
        }
    }
}
