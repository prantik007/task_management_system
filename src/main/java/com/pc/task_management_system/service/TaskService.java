package com.pc.task_management_system.service;

import com.pc.task_management_system.DTO.TaskDTO.NewTaskDTO;
import com.pc.task_management_system.entity.Project;
import com.pc.task_management_system.entity.Task;
import com.pc.task_management_system.entity.User;
import com.pc.task_management_system.repository.ProjectRepository;
import com.pc.task_management_system.repository.TaskRepository;
import com.pc.task_management_system.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }
/*
    public Task addTask(NewTaskDTO newTask){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);

        Optional<Project> project = projectRepository.findById(newTask.getProjectId());

        if(project.isEmpty())
            throw new RuntimeException("Invalid Project Id");

        if(project.get().getId() != user.get().getId())
            throw new RuntimeException("You are not authorized to add task to this project!");

        Task task = new Task();
        task.setName(newTask.getName());
        task.setProject(project.get());

        return taskRepository.save(task);
    }

 */
}
