package ru.practicum.task_tracker.Test;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_tracker.manager.FileBackedTasksManager;



import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerUnitTest extends TaskManagerTest {
    private final String fileName  = "test.txt";

    @BeforeEach
    public void beforeEach(){
        taskManager = new FileBackedTasksManager(fileName);
    }


    @Test
    public void testLoadFromFile() {
        assertTrue(taskManager.getEpics().isEmpty(),"Список не пустой ,внутри что то есть ");
        assertTrue(taskManager.getTasks().isEmpty(),"Список не пустой ,внутри что то есть ");
        assertTrue(taskManager.getSubtasks().isEmpty(),"Список не пустой ,внутри что то есть ");

        taskManager.addTask(task);
        taskManager.addNewEpic(epic);

        assertFalse(taskManager.getTasks().isEmpty(),"Список пуст");
        assertFalse(taskManager.getEpics().isEmpty(),"Список пуст");

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(fileName);
        boolean epicMap =fileBackedTasksManager1.getEpics().equals(taskManager.getEpics());
        boolean subMap = fileBackedTasksManager1.getSubtasks().equals(taskManager.getSubtasks());
        boolean taskMap = fileBackedTasksManager1.getTasks().equals(taskManager.getTasks());
        assertTrue(epicMap&&subMap&&taskMap,"Ошибка в чтении файла ");

        Throwable except = assertThrows(NullPointerException.class,()->FileBackedTasksManager.loadFromFile(null));
        assertEquals(NullPointerException.class,except.getClass(),"Происходит загрузка пустого файла");

    }
}