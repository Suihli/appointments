const Appointment = require('../models/Appointment');

// Create Appointment
exports.createAppointment = async (req, res) => {
  try {
    const { doctorName, date } = req.body;
    const newAppointment = new Appointment({ doctorName, date });
    await newAppointment.save();
    res.status(201).json(newAppointment);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

// Get All Appointments
exports.getAppointments = async (req, res) => {
  try {
    const appointments = await Appointment.find();
    res.status(200).json(appointments);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

// Delete Appointment
exports.deleteAppointment = async (req, res) => {
  try {
    const { id } = req.params;
    await Appointment.findByIdAndDelete(id);
    res.status(200).json({ message: "Appointment deleted successfully" });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

// Update Appointment
exports.updateAppointment = async (req, res) => {
  try {
    const { id } = req.params;
    const updatedAppointment = await Appointment.findByIdAndUpdate(id, req.body, { new: true });
    res.status(200).json(updatedAppointment);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
