package ru.practicum.task_tracker.manager;


import ru.practicum.task_tracker.enumereits.TaskType;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {

   protected final String fileName;
   private static File file ;



    public FileBackedTasksManager(String fileName){
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    private void save(){
        try(FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write("id,type,name,status,description,time,duration,epic\n");
            for(Task task :getTasksVal()){
                fileWriter.write(CSVFormatter.toString(task)+"\n");

            }
            for(Task task :getEpicsVal()){
                fileWriter.write(CSVFormatter.toString(task)+"\n");

            }
            for(Task task :getSubtasksVal()){
                fileWriter.write(CSVFormatter.toString(task)+"\n");

            }
            fileWriter.write("\n");
            fileWriter.write(CSVFormatter.historyToString(getHistoryManager())+"\n");
        }
        catch (IOException exp){
            exp.getMessage();
        }
    }
    public static FileBackedTasksManager loadFromFile(String fileName){
        TaskManager taskManager = new FileBackedTasksManager(fileName);
        try(FileReader fileReader = new FileReader(file.getPath())){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()){
                String line = bufferedReader.readLine();
                if(!line.equals("id,type,name,status,description,time,duration,epic")
                        &&!line.isBlank()){
                      var lineConvert = CSVFormatter.fromString(line);
                      if(lineConvert.getType().equals(TaskType.TASK)){
                          taskManager.addTask(lineConvert);
                      }
                      if(lineConvert.getType().equals(TaskType.EPIC)){
                          taskManager.addNewEpic((Epic) lineConvert);
                      }
                      if(lineConvert.getType().equals(TaskType.SUBTASK)){
                          taskManager.addSubtask((Subtask)lineConvert);
                      }

                }
                 if(line.isBlank()){
                     List<Integer> idAr = CSVFormatter.historyFromString(bufferedReader.readLine());
                    if(idAr!=null) {
                        Collections.reverse(idAr);
                         for (Integer id : idAr) {
                             taskManager.getTaskById(id);
                             taskManager.getEpicById(id);
                             taskManager.getSubtaskById(id);

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
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }
}
