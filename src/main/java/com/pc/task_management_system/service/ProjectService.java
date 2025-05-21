package com.pc.task_management_system.service;

import com.pc.task_management_system.DTO.ProjectDTO.NewProjectDTO;
import com.pc.task_management_system.DTO.TaskDTO.NewTaskDTO;
import com.pc.task_management_system.entity.Project;
import com.pc.task_management_system.entity.Task;
import com.pc.task_management_system.entity.User;
import com.pc.task_management_system.exception.UsernameNotFoundException;
import com.pc.task_management_system.repository.ProjectRepository;
import com.pc.task_management_system.repository.TaskRepository;
import com.pc.task_management_system.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository repository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = repository;
        this.taskRepository = taskRepository;
    }

    public List<Project> getAllProjectsForLoggedInUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info(username);
        if(username == null)
            throw new RuntimeException("You are not authorized or logged in");

        return projectRepository.findAllByUsername(username);
    }

    public Project createProject(NewProjectDTO projectDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(UsernameNotFoundException::new);


        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setUser(user);
        

        //project.setUser();
        Task defaultTask = Task
                .builder()
                .name("Default Task")
                .project(project)
                .build();

        project.setTasks(Set.of(defaultTask));


        Project savedProject = projectRepository.save(project);

        return savedProject;
    }

    public Task addTask(NewTaskDTO newTaskDTO, long projectId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);

        Optional<Project> project = projectRepository.findById(projectId);

        if(project.isEmpty())
            throw new RuntimeException("Invalid Project Id");


        if(project.get().getUser().getId() != user.get().getId())
            throw new RuntimeException("You are not authorized to add task to this project!");

        Task task = new Task();
        task.setName(newTaskDTO.getName());
        task.setProject(project.get());

        return taskRepository.save(task);
    }
}
