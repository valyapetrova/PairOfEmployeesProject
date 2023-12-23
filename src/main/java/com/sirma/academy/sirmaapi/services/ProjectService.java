package com.sirma.academy.sirmaapi.services;

import com.sirma.academy.sirmaapi.model.Project;
import com.sirma.academy.sirmaapi.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<Project> editProject(Long projectId, Project project) {
        Optional<Project> existingProject = projectRepository.findById(projectId);
        if (existingProject.isPresent()) {
            Project projectToToUpdate = existingProject.get();
            projectToToUpdate.setName(project.getName());
            projectToToUpdate.setDescription(project.getDescription());
            return Optional.of(projectRepository.save(projectToToUpdate));
        } else {
            return Optional.empty(); //not found
        }
    }
    public Optional<Project> viewProjectByID(Long project_id){
        return projectRepository.findById(project_id);
    }
    public List<Project> viewAllProjects(){
        return projectRepository.findAll();
    }
    public void deleteProject(Long project_id){
        projectRepository.deleteById(project_id);
    }
    public void createProject(Project project){
        projectRepository.save(project);
    }
}
