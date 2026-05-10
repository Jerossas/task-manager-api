package com.dunnwr.taskmanagerapi.models.task;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task createValidTask(){
        return new Task(
                "Estudiar",
                "Estudiar un montón de vainas",
                Priority.HIGH,
                LocalDateTime.of(2026, Month.JUNE, 1, 0, 0),
                1L,
                LocalDateTime.now()
        );
    }

    @Test
    void creatingATaskWithNullTitleShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new Task(
                null,
                null,
                null,
                null,
                null,
                null
        ));

        assertEquals("Title cannot be null.", ex.getMessage());
    }

    @Test
    void creatingATaskWithBlankTitleShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new Task(
                "",
                null,
                null,
                null,
                null,
                null
        ));

        assertEquals("Title cannot be empty.", ex.getMessage());
    }

    @Test
    void creatingATaskWithNullDescriptionShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new Task(
                "Estudiar",
                null,
                null,
                null,
                null,
                null
        ));

        assertEquals("Description cannot be null.", ex.getMessage());
    }

    @Test
    void creatingATaskWithBlankDescriptionShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new Task(
                "Estudiar",
                "",
                null,
                null,
                null,
                null
        ));

        assertEquals("Description cannot be empty.", ex.getMessage());
    }

    @Test
    void creatingATaskWithNullPriorityShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new Task(
                "Estudiar",
                "Estudiar un monton de cosas chamo",
                null,
                null,
                null,
                null
        ));

        assertEquals("Priority cannot be null.", ex.getMessage());
    }

    @Test
    void creatingATaskWithNullDueDateShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new Task(
                "Estudiar",
                "Estudiar un montón de vainas",
                Priority.HIGH,
                null,
                null,
                null
        ));

        assertEquals("Due date cannot be empty.", ex.getMessage());
    }

    @Test
    void creatingATaskWithDueDateInThePastShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new Task(
                "Estudiar",
                "Estudiar un montón de vainas",
                Priority.HIGH,
                LocalDateTime.now().minusDays(10),
                null,
                LocalDateTime.now()
        ));

        assertEquals("Due date cannot be in the past.", ex.getMessage());
    }

    @Test
    void creatingATaskWithoutAnOwnerShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new Task(
                "Estudiar",
                "Estudiar un montón de vainas",
                Priority.HIGH,
                LocalDateTime.of(2026, Month.JUNE, 1, 0, 0),
                null,
                LocalDateTime.now()
        ));

        assertEquals("User id cannot be null", ex.getMessage());
    }

    @Test
    void givenValidArguments_whenCreatingTask_ShouldReturnANewTask(){
        Task task = createValidTask();

        assertNotNull(task);
    }

    @Test
    void newTaskShouldStartWithNewStatus(){
        var task = createValidTask();
        assertEquals("NEW", task.getStatus().name());
    }

    @Test
    void changeTitle_givenANullValue_shouldThrowInvalidFieldException() {
        var task = createValidTask();
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> task.changeTitle(null));

        assertEquals("Title cannot be null.", ex.getMessage());
    }

    @Test
    void changeTitle_givenABlankTitle_shouldThrowInvalidFieldException() {
        var task = createValidTask();
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> task.changeTitle(""));

        assertEquals("Title cannot be empty.", ex.getMessage());
    }

    @Test
    void changeTitle_givenAValidTitle_shouldUpdateTitle() {
        var task = createValidTask();

        task.changeTitle("Estudiar mucho!");
        assertEquals("Estudiar mucho!", task.getTitle());
    }

    @Test
    void changeDescription_givenANullDescription_shouldThrowInvalidFieldException() {
        var task = createValidTask();
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> task.changeDescription(null));

        assertEquals("Description cannot be null.", ex.getMessage());
    }

    @Test
    void changeDescription_givenABlankDescription_shouldThrowInvalidFieldException() {
        var task = createValidTask();
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> task.changeDescription(""));

        assertEquals("Description cannot be empty.", ex.getMessage());
    }

    @Test
    void changeDescription_givenAValidDescription_shouldUpdateDescription() {
        var task = createValidTask();
        task.changeDescription("Estudiar Spring Boot");

        assertEquals("Estudiar Spring Boot", task.getDescription());
    }

    @Test
    void changePriority_givenANullPriority_shouldThrowInvalidFieldException() {
        var task = createValidTask();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> task.changePriority(null));
        assertEquals("Priority cannot be null.", ex.getMessage());
    }

    @Test
    void changePriority_givenAValidPriority_shouldUpdatePriority() {
        var task = createValidTask();

        task.changePriority(Priority.MEDIUM);
        assertEquals("MEDIUM",task.getPriority().name());
    }

    @Test
    void changeDueDate_givenANullDueDate_shouldThrowInvalidFieldException() {
        var task = createValidTask();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> task.changeDueDate(null, null));
        assertEquals("Due date cannot be empty.", ex.getMessage());
    }

    @Test
    void changeDueDate_givenAPastDate_shouldThrowInvalidFieldException() {
        var task = createValidTask();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> task.changeDueDate(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
        ));

        assertEquals("Due date cannot be in the past.", ex.getMessage());
    }

    @Test
    void changeDueDate_givenAValidDate_shouldUpdateDueDate() {
        var task = createValidTask();
        LocalDateTime newDate = LocalDateTime.now().plusDays(10);

        task.changeDueDate(newDate, LocalDateTime.now());
        assertEquals(newDate, task.getDueDate());
    }

    @Test
    void changeStatus_givenNullStatus_shouldThrowInvalidFieldException() {
        var task = createValidTask();
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> task.changeStatus(null));
        assertEquals("Status cannot be null.", ex.getMessage());
    }

    @Test
    void changeStatus_givenValidStatus_shouldUpdateStatus() {
        var task = createValidTask();
        task.changeStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void restore_givenAValidDatabaseRecord_shouldConstructANewTask() {
        Task task = Task.restore(
                1L,
                "Estudiar",
                "Estudiar mucho",
                Status.IN_PROGRESS,
                Priority.HIGH,
                1L,
                LocalDateTime.of(2026, Month.JUNE, 1, 0, 0, 0)
        );

        assertNotNull(task);
    }

}
