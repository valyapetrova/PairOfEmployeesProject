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
        if(workingHistory.getDateTo()==null){
            workingHistory.setDateTo(LocalDate.now());
        }
        if(workingHistory.getDateFrom()==null){
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


    public List<String> workedPairsForLongestPeriodv3() {
       return workHistoryRepository.workedPairsForLongestPeriodv3();
    }



//    public List<WorkingHistory> workedPairsForLongestPeriod(){
//        List<WorkingHistory>  historyList = workHistoryRepository.findAll();
//        for(WorkingHistory history : historyList){
//            Period period = Period.between(history.getDateFrom(),history.getDateTo());
//            Map<Long, List<WorkingHistory>> projectsMap = historyList.stream()
//                    .collect(Collectors.groupingBy(WorkingHistory::getProjectId));
//
//                projectsMap.forEach((projectId, workedOnForProject) -> {
//                    Map<Set<Long>, Period> pairsWorkDuration = calculatePairsWorkDuration(workedOnForProject);
//
//                    Optional<Map.Entry<Set<Long>, Period>> longestPairEntry = pairsWorkDuration.entrySet().stream()
//                            .max(Comparator.comparing(entry -> entry.getValue().getDays()));
//
//
//                    longestPairEntry.ifPresent(pairEntry -> {
//                    Set<Long> employeePair = pairEntry.getKey();
//                    Period longestDuration = pairEntry.getValue();
//
//                    System.out.println("Project ID: " + projectId);
//                    System.out.println("Employee Pair: " + employeePair);
//                    System.out.println("Longest Duration: " +
//                            longestDuration.getYears() + " years, " +
//                            longestDuration.getMonths() + " months, " +
//                            longestDuration.getDays() + " days");
//                    System.out.println("--------------");
//                });
//            });
//        }
//        return historyList;
//        }
//    private static Map<Set<Long>, Period> calculatePairsWorkDuration(List<WorkingHistory> workedOnList) {
//        Map<Set<Long>, Period> pairsWorkDuration = new HashMap<>();
//
//        for (int i = 0; i < workedOnList.size(); i++) {
//            for (int j = i + 1; j < workedOnList.size(); j++) {
//                WorkingHistory work1 = workedOnList.get(i);
//                WorkingHistory work2 = workedOnList.get(j);
//
//                // Check if the two employees worked together
//                if (work1.getEmployeeId().equals(work2.getEmployeeId())) {
//                    continue; // Skip if the same employee
//                }
//
//                Set<Long> employeePair = new HashSet<>(Arrays.asList(work1.getEmployeeId(), work2.getEmployeeId()));
//
//                // Calculate the duration of work for the pair on the common project
//                LocalDate start1 = work1.getDateFrom();
//                LocalDate end1 = work1.getDateTo();
//                LocalDate start2 = work2.getDateFrom();
//                LocalDate end2 = work2.getDateTo();
//
//                LocalDate commonStartDate = start1.isAfter(start2) ? start1 : start2;
//                LocalDate commonEndDate = end1.isBefore(end2) ? end1 : end2;
//
//                if (!commonStartDate.isAfter(commonEndDate)) {
//                    Period duration = Period.between(commonStartDate, commonEndDate);
//                    pairsWorkDuration.merge(employeePair, duration, Period::plus);
//                }
//            }
//        }
//
//        return pairsWorkDuration;
//    }
//
//    public List<WorkingHistory> workedPairsForLongestPeriodv2() {
//        List<WorkingHistory>  historyList = workHistoryRepository.findAll();
//
//        Map<Set<Long>, Map<Long, Long>> pairsWorkDuration = calculatePairsWorkDurationv2(historyList);
//
//        Optional<Map.Entry<Set<Long>, Map<Long, Long>>> longestPairEntry = pairsWorkDuration.entrySet().stream()
//                .max(Comparator.comparing(entry -> entry.getValue().values().stream()
//                        .mapToLong(period -> period)
//                        .sum()));
//
//        if (longestPairEntry.isPresent()) {
//            Map.Entry<Set<Long>, Map<Long, Long>> longestPair = longestPairEntry.get();
//            System.out.println("Employees: " + longestPair.getKey());
//
//            Map<Long, Long> projectDurations = longestPair.getValue();
//            for (Map.Entry<Long, Long> entry : projectDurations.entrySet()) {
//                System.out.println("Project " + entry.getKey() + ": " + entry.getValue() + " days");
//                return historyList;
//            }
//        } else {
//            System.out.println("No pairs found.");
//        }
//        return historyList;
//    }
//
//    private static Map<Set<Long>, Map<Long, Long>> calculatePairsWorkDurationv2(List<WorkingHistory> workedOnList) {
//        Map<Set<Long>, Map<Long, Long>> pairsWorkDuration = new HashMap<>();
//
//        for (WorkingHistory workedOn : workedOnList) {
//            Set<Long> employeePair = new HashSet<>(Arrays.asList(workedOn.getEmployeeId(), workedOn.getProjectId()));
//
//            pairsWorkDuration.computeIfAbsent(employeePair, k -> new HashMap<>());
//            Map<Long, Long> projectDurations = pairsWorkDuration.get(employeePair);
//
//            projectDurations.computeIfAbsent(workedOn.getProjectId(), k -> (long) 0);
//            long duration = projectDurations.get(workedOn.getProjectId());
//
//            LocalDate startDate = workedOn.getDateFrom();
//            LocalDate endDate = workedOn.getDateTo();
//            long  projectPeriod = ChronoUnit.DAYS.between(startDate, endDate);
//            System.out.println("project period " + projectPeriod+ " calculated from " + startDate + " " + endDate);
//            projectDurations.put(workedOn.getProjectId(), duration + projectPeriod);
//        }
//        System.out.println(pairsWorkDuration.toString());
//
//        return pairsWorkDuration;
//    }





}
