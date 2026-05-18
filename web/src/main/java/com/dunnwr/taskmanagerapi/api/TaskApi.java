package com.dunnwr.taskmanagerapi.api;

import com.dunnwr.taskmanagerapi.dto.task.CreateTaskRequest;
import com.dunnwr.taskmanagerapi.dto.task.EditTaskRequest;
import com.dunnwr.taskmanagerapi.dto.task.TaskResponse;
import com.dunnwr.taskmanagerapi.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Tag(name = "Tareas", description = "Administración de Tareas")
public interface TaskApi {

    @Operation(summary = "Crear una nueva tarea")
    @ApiResponse(
            responseCode = "201",
            description = "Tarea creada exitosamente",
            content = @Content(
                    schema = @Schema(implementation = TaskResponse.class),
                    examples = @ExampleObject(
                            name = "Tarea creada",
                            value = """
                                {
                                    "id": 1,
                                    "title": "Estudiar Spring Boot",
                                    "description": "Revisar documentación oficial",
                                    "status": "NEW",
                                    "priority": "HIGH",
                                    "dueDate": "2026-06-01T00:00:00"
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
    ResponseEntity<TaskResponse> createTask(CreateTaskRequest request, UserDetails userDetails);

    @Operation(summary = "Obtener todas las tareas del usuario autenticado")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de tareas obtenida exitosamente",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class)),
                    examples = @ExampleObject(
                            name = "Lista de tareas",
                            value = """
                                [
                                    {
                                        "id": 1,
                                        "title": "Estudiar Spring Boot",
                                        "description": "Revisar documentación oficial",
                                        "status": "NEW",
                                        "priority": "HIGH",
                                        "dueDate": "2026-06-01T00:00:00"
                                    },
                                    {
                                        "id": 2,
                                        "title": "Hacer ejercicio",
                                        "description": "Salir a trotar 30 minutos",
                                        "status": "IN_PROGRESS",
                                        "priority": "MEDIUM",
                                        "dueDate": "2026-05-20T07:00:00"
                                    }
                                ]
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
    ResponseEntity<List<TaskResponse>> findAllTasks(UserDetails userDetails);

    @Operation(summary = "Obtener una tarea específica del usuario autenticado")
    @ApiResponse(
            responseCode = "200",
            description = "Tarea encontrada exitosamente",
            content = @Content(
                    schema = @Schema(implementation = TaskResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "id": 1,
                                    "title": "Estudiar Spring Boot",
                                    "description": "Revisar documentación oficial",
                                    "status": "NEW",
                                    "priority": "HIGH",
                                    "dueDate": "2026-06-01T00:00:00"
                                }
                                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Usuario o tarea no encontrada",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": null,
                                    "message": "Task with id 1 not found.",
                                    "status": 404,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    ResponseEntity<TaskResponse> findTask(Long taskId, UserDetails userDetails);

    @Operation(summary = "Eliminar una tarea del usuario autenticado")
    @ApiResponse(
            responseCode = "204",
            description = "Tarea eliminada exitosamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Usuario o tarea no encontrada",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": null,
                                    "message": "Task with id 1 not found.",
                                    "status": 404,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    ResponseEntity<Void> deleteTask(Long taskId, UserDetails userDetails);

    @Operation(summary = "Editar una tarea del usuario autenticado")
    @ApiResponse(
            responseCode = "200",
            description = "Tarea actualizada exitosamente",
            content = @Content(
                    schema = @Schema(implementation = TaskResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "id": 1,
                                    "title": "Estudiar Spring Boot actualizado",
                                    "description": "Revisar documentación oficial",
                                    "status": "IN_PROGRESS",
                                    "priority": "HIGH",
                                    "dueDate": "2026-06-01T00:00:00"
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
                                    "field": "priority",
                                    "message": "Invalid priority value: ULTRA",
                                    "status": 400,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Usuario o tarea no encontrada",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                                {
                                    "field": null,
                                    "message": "Task with id 1 not found.",
                                    "status": 404,
                                    "timestamp": "2026-05-15T10:30:00"
                                }
                                """
                    )
            )
    )
    ResponseEntity<TaskResponse> editTask(Long taskId, EditTaskRequest request, UserDetails userDetails);
}
