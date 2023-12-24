package com.sirma.academy.sirmaapi.repositories;

import com.sirma.academy.sirmaapi.model.WorkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkHistoryRepository extends JpaRepository<WorkingHistory, Long> {

    //Use database to get required data instead of java -> utilizing group by
    @Query(
            value = "WITH ProjectDurations AS (\n" +
                    "    SELECT\n" +
                    "        a.employee_id AS employee1,\n" +
                    "        b.employee_id AS employee2,\n" +
                    "        GREATEST(a.date_from, b.date_from) AS start_date,\n" +
                    "        LEAST(a.date_to, b.date_to) AS end_date,\n" +
                    "        LEAST(a.date_to, b.date_to) - GREATEST(a.date_from, b.date_from) AS duration\n" +
                    "    FROM\n" +
                    "        work_history a\n" +
                    "    JOIN\n" +
                    "        work_history b\n" +
                    "    ON\n" +
                    "        a.employee_id < b.employee_id\n" +
                    "        AND NOT (a.date_to < b.date_from OR a.date_from > b.date_to)\n" +
                    "),\n" +
                    "ProjectDurationswithProject AS (\n" +
                    "    SELECT\n" +
                    "        a.employee_id AS employee1,\n" +
                    "        b.employee_id AS employee2,\n" +
                    "\t\ta.project_id AS projectid,\n" +
                    "        GREATEST(a.date_from, b.date_from) AS start_date,\n" +
                    "        LEAST(a.date_to, b.date_to) AS end_date,\n" +
                    "        LEAST(a.date_to, b.date_to) - GREATEST(a.date_from, b.date_from) AS duration\n" +
                    "    FROM\n" +
                    "        work_history a\n" +
                    "    JOIN\n" +
                    "        work_history b\n" +
                    "    ON\n" +
                    "        a.project_id = b.project_id\n" +
                    "        AND a.employee_id < b.employee_id\n" +
                    "        AND NOT (a.date_to < b.date_from OR a.date_from > b.date_to)\n" +
                    "),\n" +
                    "MaxSumDuration AS (\n" +
                    "SELECT\n" +
                    "    employee1,\n" +
                    "    employee2, \n" +
                    "    SUM(duration) total_duration\n" +
                    "FROM\n" +
                    "    ProjectDurationswithProject \n" +
                    "GROUP BY\n" +
                    "    employee1,\n" +
                    "    employee2\n" +
                    "ORDER BY\n" +
                    "    total_duration DESC\n" +
                    "LIMIT 1\n" +
                    ")\n" +
                    "SELECT     employee1 || ',' || \n" +
                    "    employee2 || ',' ||\n" +
                    "     total_duration AS total_duration \n" +
                    "FROM MaxSumDuration\n" +
                    "\n" +
                    "UNION\n" +
                    "\n" +
                    "SELECT\n" +
                    "    projectid|| ',' || \n" +
                    "    SUM(duration) AS total_duration\n" +
                    "FROM\n" +
                    "    ProjectDurationswithProject \n" +
                    "WHERE employee1 in (select employee1 from MaxSumDuration)\n" +
                    "AND employee2 in (select employee2 from MaxSumDuration)\n" +
                    "GROUP BY\n" +
                    "    employee1,\n" +
                    "    employee2,\n" +
                    "\tprojectid\n" +
                    "ORDER BY\n" +
                    "    total_duration DESC;\n",
    nativeQuery = true)
    public List<String> workedPairsForLongestPeriod();

}
