const express = require('express');
const mongoose = require('mongoose');
const appointmentRoutes = require('./routes/appointmentRoutes');

const app = express();
const port = 3000;

// Middleware pour analyser les données JSON
app.use(express.json());

// Connexion à MongoDB
mongoose.connect('mongodb://localhost:27017/AppointmentManagement', {
  useNewUrlParser: true,
  useUnifiedTopology: true
})
.then(() => console.log('✅ Connected to MongoDB'))
.catch(err => console.log('❌ Failed to connect to MongoDB:', err));

// Routes
app.use('/appointments', appointmentRoutes);

// Démarrer le serveur
app.listen(port, () => {
  console.log(`🚀 Server running on http://localhost:${port}`);
});
