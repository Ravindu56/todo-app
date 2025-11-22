package com.app.todo.services;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.app.todo.models.Task;
import com.app.todo.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return getAllTasks(null);
    }

    public List<Task> getAllTasks(String sort){
        List<Task> tasks = taskRepository.findAll();
        if(sort == null || sort.isEmpty()) return tasks;

        switch(sort){
            case "title":
                tasks.sort(Comparator.comparing(Task::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)));
                break;
            case "assigned":
                tasks.sort(Comparator.comparing(Task::getAssignedAt, Comparator.nullsLast(LocalDateTime::compareTo)));
                break;
            case "deadline":
                tasks.sort(Comparator.comparing(Task::getDeadlineAt, Comparator.nullsLast(LocalDateTime::compareTo)));
                break;
            case "status":
                tasks.sort(Comparator.comparing(Task::isCompleted));
                break;
            default:
                break;
        }
        return tasks;
    }

    public void createTask(String title, String description) {
        createTask(title, description, null, null);
    }

    public void createTask(String title, String description, java.time.LocalDateTime assignedAt, java.time.LocalDateTime deadlineAt){
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(false);
        task.setAssignedAt(assignedAt);
        task.setDeadlineAt(deadlineAt);
        taskRepository.save(task);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
    
    public void toggleTask(Long id){
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Task"));
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    public void updateTask(Long id, String title, String description, LocalDateTime assignedAt, LocalDateTime deadlineAt){
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Task"));
        task.setTitle(title);
        task.setDescription(description);
        task.setAssignedAt(assignedAt);
        task.setDeadlineAt(deadlineAt);
        taskRepository.save(task);
    }
}
