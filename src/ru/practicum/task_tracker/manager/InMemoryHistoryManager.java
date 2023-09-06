package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager{
    private final Map<Long,Node> nodeMap = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void addTask(Task task) {
        if(task==null){
            return;
        }

       long id  = task.getId();
       remove(id);
       linkLast(task);
       nodeMap.put(id,last);
    }
    @Override
    public void remove(long id){
        Node node  = nodeMap.remove(id);
        if(node==null){
            return;
        }
        removeNode(node);
    }
    private void removeNode(Node node){
        if (node.prev != null){
            //Нода не одна
            node.prev.next = node.next;
            if(node.next == null){
                //Удаляемая нода последняя
                last = node.prev;
            }
            else {
                //Удаляемая нода находиться в середине
                node.next.prev = node.prev;
            }
        }
        else {
            //Нода первая
            first = node.next;
            if(first == null){
                //Нода одна
                last = null;
            }
            else{
                first.prev = null;
            }
        }

    }

    private void linkLast(Task task){
        Node node = new Node(task,last,null);
        if(first == null){
            first = node;
        }
        else{
            last.next = node;
        }
        last = node;
    }
    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private ArrayList<Task> getTasks(){
        ArrayList<Task> tasks = new ArrayList<>();

        Node node  = first;
        while(node!=null){
            tasks.add(node.task);
            node = node.next;
        }
        return tasks;
    }

    public static class Node{
        Task task;
        Node prev;
        Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }
}
