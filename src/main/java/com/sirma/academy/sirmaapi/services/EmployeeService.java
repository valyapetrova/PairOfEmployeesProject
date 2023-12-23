package com.sirma.academy.sirmaapi.services;
import com.sirma.academy.sirmaapi.model.Employee;
import com.sirma.academy.sirmaapi.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public Optional<Employee> editEmployee(Long employeeId, Employee employee) {
            Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
            if (existingEmployee.isPresent()) {
                Employee employeeToUpdate = existingEmployee.get();
                employeeToUpdate.setName(employee.getName());
                return Optional.of(employeeRepository.save(employeeToUpdate));  // Save the updated employee to the database
            } else {
                return Optional.empty(); //not found
            }
    }

    public Optional<Employee> viewEmployeeById(Long employee_id){
        return employeeRepository.findById(employee_id);
    }
    public List<Employee> viewAllEmployees(){
        return employeeRepository.findAll();
    }
    public void deleteEmployee(Long employee_id){
        employeeRepository.deleteById(employee_id);
    }
}
