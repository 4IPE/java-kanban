package ru.practicum.task_tracker.manager;


import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {

   private final File file;

    private void save(){
        try(FileWriter fileWriter = new FileWriter("test.txt")){
            fileWriter.write("id,type,name,status,description,epic\n");
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
                if(!bufferedReader.readLine().equals("id,type,name,status,description,epic")){
                    CSVFormatter.fromString(bufferedReader.readLine());
                }
                 if(bufferedReader.readLine().isBlank()){
                     List<Integer> idAr = CSVFormatter.historyFromString(bufferedReader.readLine());
                     HistoryManager historyManager = new InMemoryHistoryManager();
                     FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
                    if(idAr!=null) {
                         for (Integer id : idAr) {
                             Task task = fileBackedTasksManager.getTasks().get(id);
                             Epic epic = fileBackedTasksManager.getEpics().get(id);
                             Subtask subtask = fileBackedTasksManager.getSubtasks().get(id);
                             if (task != null) {
                                 historyManager.addTask(task);
                             }
                             if (epic != null) {
                                 historyManager.addTask(epic);
                             }
                             if (subtask != null) {
                                 historyManager.addTask(subtask);
                             }
                         }
                     }
                 }
            }

        } catch (FileNotFoundException e){
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return (FileBackedTasksManager) taskManager;
    }

    public FileBackedTasksManager(File file){
        this.file = file;
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

    public File getFile() {
        return file;
    }
}
