package com.pc.task_management_system.controller;

import com.pc.task_management_system.DTO.ProjectDTO.NewProjectDTO;
import com.pc.task_management_system.DTO.ProjectDTO.SavedProjectDTO;
import com.pc.task_management_system.DTO.TaskDTO.NewTaskDTO;
import com.pc.task_management_system.DTO.TaskDTO.SavedTaskDTO;
import com.pc.task_management_system.entity.Project;
import com.pc.task_management_system.entity.Task;
import com.pc.task_management_system.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/projects")
public class ProjectController {

    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<SavedProjectDTO>> getAllProjectsFromLoggedInUser(){

        List<SavedProjectDTO> projectDTOS = projectService.getAllProjectsForLoggedInUser()
                .stream()
                .map(project -> SavedProjectDTO.builder()
                        .id(project.getId())
                        .name(project.getName())
                        .build())
                .toList();

        return ResponseEntity
                .ok()
                .body(projectDTOS);
    }

    @PostMapping
    public ResponseEntity<SavedProjectDTO> addProject(@RequestBody NewProjectDTO project){

        Project savedProject = projectService.createProject(project);

        SavedProjectDTO savedProjectDTO = SavedProjectDTO
                .builder()
                .id(savedProject.getId())
                .name(savedProject.getName())
                .build();
        return ResponseEntity
                .ok()
                .body(savedProjectDTO);
    }

    @PostMapping("/{projectId}/tasks")
    public SavedTaskDTO addTask(@RequestBody NewTaskDTO newTaskDTO, @PathVariable("projectId") long projectId){
        Task task = projectService.addTask(newTaskDTO, projectId);

        SavedTaskDTO savedTaskDTO = SavedTaskDTO
                .builder()
                .id(task.getId())
                .name(task.getName())
                .projectId(task.getProject().getId())
                .build();

        return savedTaskDTO;
    }
}
