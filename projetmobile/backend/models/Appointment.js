const mongoose = require('mongoose');

// Schéma de rendez-vous
const appointmentSchema = new mongoose.Schema({
  doctorName: {
    type: String,
    required: true,
    trim: true
  },
  date: {
    type: String,
    required: true,
    trim: true
  }
});

module.exports = mongoose.model('Appointment', appointmentSchema);
