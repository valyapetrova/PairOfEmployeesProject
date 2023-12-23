package com.sirma.academy.sirmaapi.repositories;

import com.sirma.academy.sirmaapi.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
