package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.enumereits.TaskType;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.io.*;
import java.util.ArrayList;


public class FileBackedTasksManager extends InMemoryTaskManager {



    public void save(){
        try(FileWriter fileWriter = new FileWriter("test.txt")){
            fileWriter.write("id,type,name,status,description,epicID\n");
            for(Task task :getTasksVal()){
                fileWriter.write(CSVFormatter.toString(task));
                fileWriter.write("\n");
            }
            for(Task task :getEpicsVal()){
                fileWriter.write(CSVFormatter.toString(task));
                fileWriter.write("\n");
            }
            for(Task task :getSubtasksVal()){
                fileWriter.write(CSVFormatter.toString(task));
                fileWriter.write("\n");
            }
            fileWriter.write("\n");
            fileWriter.write(CSVFormatter.historyToString(getHistoryManager()));
        }
        catch (IOException exp){
            exp.getMessage();
        }
    }
    public static FileBackedTasksManager loadFromFile(File file){
        TaskManager taskManager = new FileBackedTasksManager(file);
        try(FileReader fileReader = new FileReader(file.getPath())){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()){
                String[]lineAr = bufferedReader.readLine().split(",");
                for(String element:lineAr ){
                 if(element.equals(TaskType.TASK)){
                     Task task = new Task(lineAr[2],lineAr[4]);
                     task.setId(Integer.parseInt(lineAr[0]));
                     task.setStatus(TaskStatus.valueOf(lineAr[3]));
                     taskManager.addTask(task);
                 }
                 if(element.equals(TaskType.EPIC)){
                        Epic epic = new Epic(lineAr[2],lineAr[4]);
                        epic.setId(Integer.parseInt(lineAr[0]));
                        epic.setStatus(TaskStatus.valueOf(lineAr[3]));
                        taskManager.addNewEpic(epic);
                 }
                 if(element.equals(TaskType.SUBTASK)){
                     Subtask subtask = new Subtask(lineAr[2],lineAr[4],Long.parseLong(lineAr[5]));
                     subtask.setId(Integer.parseInt(lineAr[0]));
                     subtask.setStatus(TaskStatus.valueOf(lineAr[3]));
                     taskManager.addSubtask(subtask);
                 }
                 if(bufferedReader.readLine().isBlank()){
                     HistoryManager historyManager = new InMemoryHistoryManager();
                     FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
                     for(int i =0;i<lineAr.length;i++){
                         Task task = fileBackedTasksManager.getTasks().get(Long.parseLong(lineAr[i]));
                         Epic epic = fileBackedTasksManager.getEpics().get(Long.parseLong(lineAr[i]));
                         Subtask subtask = fileBackedTasksManager.getSubtasks().get(Long.parseLong(lineAr[i]));
                         if(task!=null){
                             historyManager.addTask(task);
                         }
                         if (epic!=null){
                             historyManager.addTask(epic);
                         }
                         if (subtask!=null){
                             historyManager.addTask(subtask);
                         }
                     }
                 }
                }

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FileBackedTasksManager(file);
    }

    public FileBackedTasksManager(File file){

    }

    @Override
    public Long addNewEpic(Epic epic){
        Long id =super.addNewEpic(epic);
        save();

        return id;
    }
    @Override
    public Long addSubtask(Subtask subtask){
        Long id =super.addSubtask(subtask);
        save();
        return id;
        }
    @Override
    public Long addTask(Task task){
       Long id = super.addTask(task);
       save();
        return id;
    }

    @Override
    public Task getTaskById(long id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(long id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(long id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public ArrayList<Subtask> gettingSubtaskFromEpic(long epicId) {
        ArrayList<Subtask> subtaskArrayList = super.gettingSubtaskFromEpic(epicId);
        save();
        return subtaskArrayList;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        FileBackedTasksManager o = (FileBackedTasksManager) obj;
        boolean taskEquals = getTasks().equals(o.getTasks());
        boolean epicEquals = getEpics().equals(o.getEpics());
        boolean subEquals = getSubtasks().equals(o.getSubtasks());
        if(taskEquals&&epicEquals&&subEquals) return true;
        return false;
    }
}
