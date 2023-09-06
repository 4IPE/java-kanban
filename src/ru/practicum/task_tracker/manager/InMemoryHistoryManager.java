package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.CustomLinkedList;
import ru.practicum.task_tracker.CustomLinkedList.Node;
import ru.practicum.task_tracker.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager{
   private final CustomLinkedList customLinkedList = new CustomLinkedList();
    private final Map<Long, Node> nodeMap = customLinkedList.getNodeMap();
    private static final int LIMIT = 10;
    @Override
    public void addTask(Task task) {
        if(task==null){
            return;
        }
        long id = task.getId();
        remove(id);
        customLinkedList.linkLast(task);
        nodeMap.put(id, customLinkedList.getLast());
        if (nodeMap.size()>LIMIT) {
            remove(customLinkedList.getFirst().task.getId());
        }

    }
    @Override
    public void remove(long id){
        Node node  = nodeMap.remove(id);
        if(node==null){
            return;
        }
        customLinkedList.removeNode(node);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private ArrayList<Task> getTasks(){
        ArrayList<Task> tasks = new ArrayList<>();

        Node node  = customLinkedList.getFirst();
        while(node!=null){
            tasks.add(node.task);
            node = node.next;
        }
        Collections.reverse(tasks);
        return tasks;
    }


}
