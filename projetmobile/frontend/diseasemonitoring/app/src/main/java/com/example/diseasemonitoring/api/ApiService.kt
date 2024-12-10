import com.example.diseasemonitoring.models.Appointment
import retrofit2.Response
import retrofit2.http.*

interface AppointmentApi {

    // Récupérer tous les rendez-vous
    @GET("appointments")
    suspend fun getAppointments(): Response<List<Appointment>>

    // Ajouter un rendez-vous
    @POST("appointments")
    suspend fun createAppointment(@Body appointment: Appointment): Response<Appointment>

    // Mettre à jour un rendez-vous
    @PUT("appointments/{id}")
    suspend fun updateAppointment(@Path("id") id: String, @Body appointment: Appointment): Response<Appointment>

    // Supprimer un rendez-vous
    @DELETE("appointments/{id}")
    suspend fun deleteAppointment(@Path("id") id: String): Response<Void>
}
