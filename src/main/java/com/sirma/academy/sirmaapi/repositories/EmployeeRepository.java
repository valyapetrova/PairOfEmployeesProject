package com.sirma.academy.sirmaapi.repositories;

import com.sirma.academy.sirmaapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}
