const express = require('express');
const { 
  createAppointment, 
  getAppointments, 
  deleteAppointment, 
  updateAppointment 
} = require('../controllers/appointmentController');

const router = express.Router();

// CRUD Routes
router.post('/', createAppointment);
router.get('/', getAppointments);
router.delete('/:id', deleteAppointment);
router.put('/:id', updateAppointment);

module.exports = router;
