package com.sirma.academy.sirmaapi.services;

import com.sirma.academy.sirmaapi.model.WorkingHistory;
import com.sirma.academy.sirmaapi.repositories.WorkHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkingHistoryService {
    private final WorkHistoryRepository workHistoryRepository;

    @Autowired
    public WorkingHistoryService(WorkHistoryRepository workHistoryRepository) {
        this.workHistoryRepository = workHistoryRepository;
    }

    public void saveWorkingHistory(WorkingHistory workingHistory) {
        if (workingHistory.getDateTo() == null) {
            workingHistory.setDateTo(LocalDate.now());
        }
        if (workingHistory.getDateFrom() == null) {
            workingHistory.setDateFrom(LocalDate.now());
        }
        workHistoryRepository.save(workingHistory);
    }

    public List<WorkingHistory> getAllWorkingHistory() {
        return workHistoryRepository.findAll();
    }

    public Optional<WorkingHistory> editWorkingHistory(Long workHistoryId, WorkingHistory workingHistory) {
        Optional<WorkingHistory> existingHistory = workHistoryRepository.findById(workHistoryId);
        if (existingHistory.isPresent()) {
            WorkingHistory historyToUpdate = existingHistory.get();
            historyToUpdate.setEmployeeId(workingHistory.getEmployeeId());
            historyToUpdate.setProjectId(workingHistory.getProjectId());
            return Optional.of(workHistoryRepository.save(historyToUpdate));
        } else {
            return Optional.empty(); //not found
        }
    }

    public List<String> workedPairsForLongestPeriod() {
        return workHistoryRepository.workedPairsForLongestPeriod();
    }

}
