package com.sirma.academy.sirmaapi.controllers;

import com.sirma.academy.sirmaapi.model.Employee;
import com.sirma.academy.sirmaapi.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:8081")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public String createEmployee(@RequestBody Employee employee){
        //TODO validate data
        try {
            employeeService.addEmployee(employee);
        }catch(Exception exception){
            System.out.println("Error with creating an employee!");
        }
        return "Successss! Hooray!";
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.viewEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllEmployees(){
       return new ResponseEntity<>(employeeService.viewAllEmployees(),HttpStatus.OK) ;
    }
    @PostMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editEmployee(@PathVariable Long id, @RequestBody Employee employee) {
            try {
                Optional<Employee> updatedEmployee = employeeService.editEmployee(id, employee);
                if (updatedEmployee.isPresent()) {
                    return ResponseEntity.ok("Employee updated successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
    }

}
