package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.CustomLinkedList;
import ru.practicum.task_tracker.CustomLinkedList.Node;
import ru.practicum.task_tracker.tasks.Task;

import java.util.Comparator;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
   private final CustomLinkedList customLinkedList = new CustomLinkedList();
    private final Map<Long, Node> nodeMap = customLinkedList.getNodeMap();
    private static final int LIMIT = 10;

    public Map<Long, Node> getNodeMap() {
        return nodeMap;
    }

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
    @Override
    public Set<Task> getPrioritizedTasks(){
        Set<Task> taskTreeSet = new TreeSet<>(new DateTimeComparator());
        ArrayList<Task> historyArr = (ArrayList<Task>) getHistory();
        taskTreeSet.addAll(historyArr);
        return taskTreeSet;
    }
    static class DateTimeComparator implements Comparator<Task> { // на месте T - класс Item

        @Override
        public int compare(Task task1, Task task2) {

            // сравниваем товары — более дорогой должен быть дальше в списке
            if (task1.getStartTime().isBefore(task2.getStartTime())) {
                return 1;

                // более дешёвый — ближе к началу списка
            } else if (task1.getStartTime().isAfter(task2.getStartTime())) {
                return -1;

                // если стоимость равна, нужно вернуть 0
            } else {
                return 0;
            }
        }
    }



}
