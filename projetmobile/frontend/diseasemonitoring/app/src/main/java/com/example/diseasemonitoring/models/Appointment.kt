package com.example.diseasemonitoring.models

data class Appointment(
    val _id: String? = null,   // ID optionnel
    val doctorName: String,   // Nom du médecin
    val date: String           // Date du rendez-vous
)
