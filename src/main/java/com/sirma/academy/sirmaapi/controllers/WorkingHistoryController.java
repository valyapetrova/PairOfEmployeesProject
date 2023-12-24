package com.sirma.academy.sirmaapi.controllers;

import com.sirma.academy.sirmaapi.model.WorkingHistory;
import com.sirma.academy.sirmaapi.services.FileService;
import com.sirma.academy.sirmaapi.services.WorkingHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "http://localhost:8081")
public class WorkingHistoryController {
    private final WorkingHistoryService workingHistoryService;
    private final FileService fileService;

    public WorkingHistoryController(WorkingHistoryService workingHistoryService, FileService fileService) {
        this.workingHistoryService = workingHistoryService;
        this.fileService = fileService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createWorkingHistory(@RequestBody WorkingHistory workingHistory) {
        workingHistoryService.saveWorkingHistory(workingHistory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<WorkingHistory>> getWorkingHistory() {
        fileService.readFile();
        return new ResponseEntity<>(workingHistoryService.getAllWorkingHistory(), HttpStatus.OK);
    }

    @GetMapping("/getlongestpair")
    public ResponseEntity<List<String>> getLongestPair() {
        System.out.println(workingHistoryService.workedPairsForLongestPeriod());
        return new ResponseEntity<>(workingHistoryService.workedPairsForLongestPeriod(), HttpStatus.OK);
    }

        @PostMapping("/edit/{id}")
    public ResponseEntity<String> editHistory(@PathVariable Long id, @RequestBody WorkingHistory workingHistory) {
        try {
            Optional<WorkingHistory> updatedHistory = workingHistoryService.editWorkingHistory(id, workingHistory);
            if (updatedHistory.isPresent()) {
                List<WorkingHistory> workHistoryList = workingHistoryService.getAllWorkingHistory();
                return ResponseEntity.ok("History updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Such history was not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating history");
        }
    }
}