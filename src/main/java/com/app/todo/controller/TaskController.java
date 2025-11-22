package com.app.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.todo.models.Task;
import com.app.todo.services.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getTasks(Model model, @RequestParam(required = false, name = "sort") String sort){
        List<Task> tasks = taskService.getAllTasks(sort);
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentSort", sort);
        return "tasks";
    }

    @PostMapping
    public String createTask(@RequestParam String title,
                             @RequestParam String description,
                             @RequestParam(required = false, name = "assignDate") String assignDate,
                             @RequestParam(required = false, name = "deadlineDate") String deadlineDate){

        LocalDateTime assignedAt = null;
        LocalDateTime deadlineAt = null;
        try {
            if(assignDate != null && !assignDate.isEmpty()){
                assignedAt = LocalDateTime.parse(assignDate);
            }
            if(deadlineDate != null && !deadlineDate.isEmpty()){
                deadlineAt = LocalDateTime.parse(deadlineDate);
            }
        } catch (Exception ex) {
            // If parsing fails, we ignore and leave null (could log in future)
        }

        taskService.createTask(title, description, assignedAt, deadlineAt);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
    
    @GetMapping("/{id}/toggle")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleTask(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/edit")
    public String editTask(@PathVariable Long id,
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam(required = false, name = "assignDate") String assignDate,
                           @RequestParam(required = false, name = "deadlineDate") String deadlineDate){

        LocalDateTime assignedAt = null;
        LocalDateTime deadlineAt = null;
        try {
            if(assignDate != null && !assignDate.isEmpty()){
                assignedAt = LocalDateTime.parse(assignDate);
            }
            if(deadlineDate != null && !deadlineDate.isEmpty()){
                deadlineAt = LocalDateTime.parse(deadlineDate);
            }
        } catch (Exception ex) {
            // ignore parse errors
        }

        taskService.updateTask(id, title, description, assignedAt, deadlineAt);
        return "redirect:/tasks";
    }
}
