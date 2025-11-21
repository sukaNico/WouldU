package com.example.dilemario.data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// ----------------------------
// REQUESTS / RESPONSES LOGIN
// ----------------------------
data class LoginRequest(
    val email: String,
    val password: String
)

data class UserData(
    val id: Int,
    val nombre: String,
    val email: String,
    val edad_rango: String,
    val pais: String
)

data class LoginData(
    val user: UserData,
    val token: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val data: LoginData?
)


// ----------------------------
// MODELOS PARA DILEMAS (según tu JSON EXACTO)
// ----------------------------
data class DilemaApi(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val opcion_a: String,
    val opcion_b: String,
    val categoria: String,
    val creado_por: Int?,
    val activo: Boolean,
    val created_at: String,
    val total_denuncias: Int,
    val creador_nombre: String?
)

data class DilemaListResponse(
    val success: Boolean,
    val message: String?,
    val data: List<DilemaApi>
)

data class RespuestaDilemaResponse(
    val success: Boolean,
    val message: String,
    val data: RespuestaData
)

data class RespuestaData(
    val opcion_elegida: String,
    val estadisticas: Estadisticas
)

data class Estadisticas(
    val total_votos: Int,
    val porcentaje_a: Int,
    val porcentaje_b: Int,
    val votos_a: Int,
    val votos_b: Int
)

data class DilemaApiResponse(
    val success: Boolean,
    val data: DilemaApi
)

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String,
    val edad_rango: String,
    val pais: String
)

data class RegisterData(
    val user: UserData,
    val token: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val data: RegisterData?
)

// ----------------------------
// API SERVICE
// ----------------------------
interface ApiService {

    // LOGIN
    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    // REGISTER
    @POST("api/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @GET("api/dilemas/{userId}/unanswered")
    suspend fun getDilemasUnanswered(@Path("userId") userId: Int): DilemaListResponse

    @POST("api/dilemas/{id}/responder")
    suspend fun responderDilema(
        @Path("id") id: Int,
        @Body body: Map<String, String>
    ): RespuestaDilemaResponse

    @GET("api/dilemas/user/{userId}")
    suspend fun getDilemasUsuario(@Path("userId") userId: Int): DilemaListResponse

    @GET("api/dilemas/{id}")
    suspend fun getDilema(@Path("id") id: Int): DilemaApiResponse

    @PUT("api/dilemas/{id}")
    suspend fun actualizarDilema(@Path("id") id: Int, @Body body: Map<String, String>): DilemaApiResponse

    @DELETE("api/dilemas/{id}")
    suspend fun eliminarDilema(@Path("id") id: Int): DilemaApiResponse

    // NUEVA FUNCIÓN → Crear un dilema
    @POST("api/dilemas")
    suspend fun crearDilema(
        @Body body: Map<String, String> // cuerpo: titulo, descripcion, opcion_a, opcion_b, categoria, creado_por
    ): DilemaApiResponse
}
