package com.dunnwr.taskmanagerapi.api;

import com.dunnwr.taskmanagerapi.dto.user.*;
import com.dunnwr.taskmanagerapi.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuarios", description = "Administración de recursos de usuarios.")
public interface UserApi {

    @Operation(summary = "Editar información básica del usuario autenticado")
    @ApiResponse(
            responseCode = "200",
            description = "Información actualizada exitosamente",
            content = @Content(
                    schema = @Schema(implementation = UserResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "firstName": "Joan",
                                    "middleName": "Ferley",
                                    "lastName": "Mosquera Lozano",
                                    "email": "example@domain.com",
                                    "gender": "MALE",
                                    "roles": ["USER"]
                                }
                                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Campo inválido en la solicitud",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": "gender",
                                    "message": "Invalid gender value: OTRO",
                                    "status": 400,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": null,
                                    "message": "User not found.",
                                    "status": 404,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    ResponseEntity<UserResponse> editUserBasicInformation(EditUserBasicInformationRequest request, UserDetails userDetails);

    @Operation(summary = "Obtener el perfil del usuario autenticado")
    @ApiResponse(
            responseCode = "200",
            description = "Perfil obtenido exitosamente",
            content = @Content(
                    schema = @Schema(implementation = UserResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "firstName": "Joan",
                                    "middleName": "Ferley",
                                    "lastName": "Mosquera Lozano",
                                    "email": "example@domain.com",
                                    "gender": "MALE",
                                    "roles": ["USER"]
                                }
                                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": null,
                                    "message": "User not found.",
                                    "status": 404,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    ResponseEntity<UserResponse> getUserProfile(UserDetails userDetails);

    @Operation(summary = "Actualizar la contraseña del usuario autenticado")
    @ApiResponse(
            responseCode = "200",
            description = "Contraseña actualizada exitosamente",
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
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": null,
                                    "message": "User not found.",
                                    "status": 404,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    ResponseEntity<TokenResponse> updatePassword(UpdatePasswordRequest request, UserDetails userDetails);

    @Operation(summary = "Eliminar la cuenta del usuario autenticado")
    @ApiResponse(
            responseCode = "204",
            description = "Usuario eliminado exitosamente"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Contraseña incorrecta",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": "currentPassword",
                                    "message": "Incorrect current password. Try again.",
                                    "status": 400,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": null,
                                    "message": "User not found.",
                                    "status": 404,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    ResponseEntity<Void> deleteUser(DeleteUserRequest request, UserDetails userDetails);
}
