package com.sirma.academy.sirmaapi.services;

import com.sirma.academy.sirmaapi.model.WorkingHistory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService implements Serializable {
    String filename = "C:/Users/valya/OneDrive/SirmaAcademy/sirmaapi/src/main/java/com/sirma/academy/sirmaapi/work_history.csv";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<WorkingHistory> readFile() {
        List<WorkingHistory> workHistory = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.trim().isEmpty() || line.startsWith("#") || line.startsWith("\"id")) {
                    continue;
                }
                String[] values = line.split(",");
                System.out.println(Arrays.toString(values));
                workHistory.add(new WorkingHistory(
                        Long.parseLong(values[1]),
                        Long.parseLong(values[2]),
                        LocalDate.parse(values[3].substring(1, values[3].length()-1), dateFormatter),
                        LocalDate.parse(values[4].substring(1, values[4].length()-1), dateFormatter)

                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workHistory;
    }
    public static void writeToFile(List<WorkingHistory> workingHistory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/valya/OneDrive/SirmaAcademy/sirmaapi/src/main/java/com/sirma/academy/sirmaapi/work_history.csv"))) {
            for (WorkingHistory history : workingHistory) {
                String line = history.getProjectId() + ","
                        + history.getEmployeeId() + ","
                        + dateFormatter.format(history.getDateFrom()) + ","
                        + dateFormatter.format(history.getDateTo()) + "\n";
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}