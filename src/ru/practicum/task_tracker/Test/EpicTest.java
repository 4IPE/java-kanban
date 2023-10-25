package ru.practicum.task_tracker.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private static Epic epic;

    @BeforeEach
    public  void beforeEach(){

        epic = new Epic("Test","Test","22.02.2022 22:22",0);
    }

    @Test
    public void dontHaveSubtaskInEpic(){
        assertEquals(0,epic.getSubtaskIds().size());
    }
    @Test
    public void onlyNewStatusSubtask(){
        Subtask subtask1 = new Subtask("Test","Test",epic.getId(),"22.02.2022 22:22",0);
        Subtask subtask2 = new Subtask("Test","Test",epic.getId(),"22.02.2022 22:22",0);
        epic.addSubtaskId(subtask1.getId());
        epic.addSubtaskId(subtask2.getId());

        assertTrue(subtask1.getStatus().equals(TaskStatus.NEW)&&subtask2.getStatus().equals(TaskStatus.NEW)&&epic.getSubtaskIds().size()!=0);

    }
    @Test
    public void onlyDoneStatusSubtask(){
        Subtask subtask1 = new Subtask("Test","Test",epic.getId(),"22.02.2022 22:22",0);
        Subtask subtask2 = new Subtask("Test","Test",epic.getId(),"22.02.2022 22:22",0);
        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        epic.addSubtaskId(subtask1.getId());
        epic.addSubtaskId(subtask2.getId());
        assertTrue(subtask1.getStatus().equals(TaskStatus.DONE)&&subtask2.getStatus().equals(TaskStatus.DONE)&&epic.getSubtaskIds().size()!=0);
    }


    @Test
    public void haveDoneAndNewStatusSubtask(){
        Subtask subtask1 = new Subtask("Test","Test",epic.getId(),"22.02.2022 22:22",0);
        Subtask subtask2 = new Subtask("Test","Test",epic.getId(),"22.02.2022 22:22",0);
        epic.addSubtaskId(subtask1.getId());
        epic.addSubtaskId(subtask2.getId());
        subtask2.setStatus(TaskStatus.DONE);
        assertTrue(subtask1.getStatus().equals(TaskStatus.NEW)&&subtask2.getStatus().equals(TaskStatus.DONE)&&epic.getSubtaskIds().size()!=0);
    }

    @Test
    public void haveInProgressStatusSubtask(){
        Subtask subtask1 = new Subtask("Test","Test",epic.getId(),"22.02.2022 22:22",0);
        Subtask subtask2 = new Subtask("Test","Test",epic.getId(),"22.02.2022 22:22",0);
        epic.addSubtaskId(subtask1.getId());
        epic.addSubtaskId(subtask2.getId());
        subtask2.setStatus(TaskStatus.DONE);
        assertTrue(subtask1.getStatus().equals(TaskStatus.NEW)&&subtask2.getStatus().equals(TaskStatus.DONE)&&epic.getSubtaskIds().size()!=0);
    }

}