package com.sirma.academy.sirmaapi.controllers;

import com.sirma.academy.sirmaapi.model.Project;
import com.sirma.academy.sirmaapi.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:8081")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("/create")
    public String createProject(@RequestBody Project project){
        //TODO validate data
        try {
            projectService.createProject(project);
        }catch(Exception exception){
            System.out.println("Error with creating a project!");
        }
        return "Successss! Hooray!";
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllProjects(){
        return new ResponseEntity<>(projectService.viewAllProjects(),HttpStatus.OK) ;
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Project> project = projectService.viewProjectByID(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editProject(@PathVariable Long id, @RequestBody Project project) {
    try {
        Optional<Project> updatedProject = projectService.editProject(id, project);
        if (updatedProject.isPresent()) {
            return ResponseEntity.ok("Project updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
    }
}
    @PostMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        projectService.deleteProject(id);
    }


}
