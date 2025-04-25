package com.example.digitalshop.data.services.model

//para login

data class LoginRequest(
    val email: String,
    val password: String
)

data class Role(
    val userId: Int,
    val roleId: Int,
    val role: RoleDetails
)

data class RoleDetails(
    val id: Int,
    val name: String
)

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val active: Boolean,
    val roles: List<Role>
)

data class LoginResponse(
    val access_token: String,
    val user: UserResponse
)

//para registrar nuevo usuario

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val roles: List<Int> = listOf(1)
)

data class RoleInfo(
    val id: Int,
    val name: String
)

data class UserRoleResponse(
    val userId: Int,
    val roleId: Int,
    val role: RoleInfo
)
data class RegisterResponse(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val active: Boolean,
    val roles: List<UserRoleResponse>
)

//para restablecer contraseña

data class ResetPasswordRequest(
    val email: String
)


data class VerifyCodeRequest(
    val email: String,
    val code: String
)

data class VerifyCodeResponse(
    val valid: Boolean
)

data class ResetPasswordConfirmationRequest(
    val email: String,
    val code: String,
    val newPassword: String
)

data class CommonResponse(
    val message: String,
    val status: String
)