package com.sirma.academy.sirmaapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "work_history")
public class WorkingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "date_from")
    @JsonFormat(pattern="[yyyy/MM/dd][yyyy-MM-dd][dd/MM/yyyy][dd-MM-yyyy][yyyy:MM:dd]")
    private LocalDate dateFrom;
    @JsonFormat(pattern="[yyyy/MM/dd][yyyy-MM-dd][dd/MM/yyyy][dd-MM-yyyy][yyyy:MM:dd]")
    @Column(name = "date_to")
    private LocalDate dateTo;

    public WorkingHistory() {
    }
    public WorkingHistory(Long projectId, Long employeeId, LocalDate dateFrom, LocalDate dateTo) {
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
    public LocalDate getDateFrom() {
        return dateFrom;
    }
}