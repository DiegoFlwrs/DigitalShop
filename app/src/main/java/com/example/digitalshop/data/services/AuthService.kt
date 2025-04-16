package com.example.digitalshop.data.services
import com.example.digitalshop.data.services.model.CommonResponse
import com.example.digitalshop.data.services.model.LoginRequest
import com.example.digitalshop.data.services.model.LoginResponse
import com.example.digitalshop.data.services.model.RegisterRequest
import com.example.digitalshop.data.services.model.RegisterResponse
import com.example.digitalshop.data.services.model.ResetPasswordConfirmationRequest
import com.example.digitalshop.data.services.model.ResetPasswordRequest
import com.example.digitalshop.data.services.model.VerifyCodeRequest
import com.example.digitalshop.data.services.model.VerifyCodeResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    fun login(@Body credentials: LoginRequest): Call<LoginResponse>
    // Enviar código de restablecimiento al correo
    @POST("auth/send-code")
    suspend fun sendResetCode(@Body request: ResetPasswordRequest): Response<CommonResponse>

    // Verificar el código
    @POST("auth/verify-code")
    suspend fun verifyCode(@Body request: VerifyCodeRequest): Response<VerifyCodeResponse>

    // Restablecer la contraseña
    @POST("auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordConfirmationRequest): Response<CommonResponse>

}
