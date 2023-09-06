package ru.practicum.task_tracker;

import ru.practicum.task_tracker.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class CustomLinkedList {
    private final Map<Long,Node> nodeMap = new HashMap<>();
    private Node first;
    private Node last;

    public Node getFirst() {
        return first;
    }

    public Node getLast() {
        return last;
    }

    public Map<Long, Node> getNodeMap() {
        return nodeMap;
    }

    public void removeNode(Node node){
        if (node.prev != null){
            //Нода не первая
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

    public void linkLast(Task task){
        Node node = new Node(task,last,null);
        if(first == null){
            first = node;
        }
        else{
            last.next = node;
        }
        last = node;
    }



    public static class Node{
        public  Task task;
        public Node prev;
        public Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }
}
