package com.dunnwr.taskmanagerapi.api;

import com.dunnwr.taskmanagerapi.dto.user.SignInUserRequest;
import com.dunnwr.taskmanagerapi.dto.user.SignUpUserRequest;
import com.dunnwr.taskmanagerapi.dto.user.TokenResponse;
import com.dunnwr.taskmanagerapi.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticación y Autorización", description = "Permisos e identidad de usuarios")
public interface AuthApi {

    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponse(
            responseCode = "201",
            description = "Usuario registrado exitosamente"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Campo inválido en la solicitud",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": "confirmPassword",
                                    "message": "Passwords do not match.",
                                    "status": 400,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "El correo ya está registrado",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": null,
                                    "message": "Email example@domain.com is already registered. Try with a different one.",
                                    "status": 409,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    ResponseEntity<Void> signUpUser(SignUpUserRequest request);

    @Operation(summary = "Iniciar sesión")
    @ApiResponse(
            responseCode = "200",
            description = "Sesión iniciada exitosamente",
            content = @Content(
                    schema = @Schema(implementation = TokenResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "token": "eyJhbGciOiJIUzI1NiJ9..."
                                }
                                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": null,
                                    "message": "Invalid email or password.",
                                    "status": 401,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    ResponseEntity<TokenResponse> singInUser(SignInUserRequest request);

    @Operation(summary = "Cerrar sesión")
    @ApiResponse(
            responseCode = "204",
            description = "Sesión cerrada exitosamente"
    )
    ResponseEntity<Void> signOutUser();
}
