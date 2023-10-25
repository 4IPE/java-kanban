package ru.practicum.task_tracker.Test;


import org.junit.jupiter.api.Test;
import ru.practicum.task_tracker.manager.FileBackedTasksManager;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerUnitTest extends TaskManagerTest {
    private final File file  = new File("test.txt");
    private final FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);


    @Test
    public void testLoadFromFile(){
        assertTrue(fileBackedTasksManager.getEpics().isEmpty(),"Список не пустой ,внутри что то есть ");
        assertTrue(fileBackedTasksManager.getTasks().isEmpty(),"Список не пустой ,внутри что то есть ");
        assertTrue(fileBackedTasksManager.getSubtasks().isEmpty(),"Список не пустой ,внутри что то есть ");

        fileBackedTasksManager.addTask(task);
        fileBackedTasksManager.addNewEpic(epic);

        assertFalse(fileBackedTasksManager.getTasks().isEmpty(),"Список пуст");
        assertFalse(fileBackedTasksManager.getEpics().isEmpty(),"Список пуст");

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        boolean epicMap =fileBackedTasksManager1.getEpics().equals(fileBackedTasksManager.getEpics());
        boolean subMap = fileBackedTasksManager1.getSubtasks().equals(fileBackedTasksManager.getSubtasks());
        boolean taskMap = fileBackedTasksManager1.getTasks().equals(fileBackedTasksManager.getTasks());
        assertTrue(epicMap&&subMap&&taskMap,"Ошибка в чтении файла ");

        Throwable except = assertThrows(NullPointerException.class,()->FileBackedTasksManager.loadFromFile(null));
        assertEquals(NullPointerException.class,except.getClass(),"Происходит загрузка пустого файла");

    }
}