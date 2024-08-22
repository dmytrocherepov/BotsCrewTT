package org.example.botscrewtesttask;

import lombok.RequiredArgsConstructor;
import org.example.botscrewtesttask.service.DepartmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class BotsCrewTestTaskApplication implements CommandLineRunner {
    private static final String FIRST_COMMAND = "Who is head of department";
    private static final String SECOND_COMMAND = "Show the average salary for the department";
    private static final String THIRD_COMMAND = "Show count of employee for";
    private static final String FOURTH_COMMAND = "Global search by";
    private static final String STATISTICS_START = "Show";
    private static final String STATISTICS_END = "statistics";
    private static final String EXIT = "exit";
    private final DepartmentService departmentService;

    public static void main(String[] args) {
        SpringApplication.run(BotsCrewTestTaskApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("""
                Write your command below
                Command list:
                Who is head of department {department_name}
                Show {department_name} statistics
                Show the average salary for the department {department_name}
                Show count of employee for {department_name}
                Global search by {template}
                """);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                if (input.startsWith(FIRST_COMMAND)) {
                    String departmentName = input.substring(FIRST_COMMAND.length()).trim();
                    String headOfTheDepartment = departmentService.getHeadOfTheDepartment(departmentName);
                    System.out.println("Head of " + departmentName + " department is " + headOfTheDepartment);
                } else if (input.startsWith(SECOND_COMMAND)) {
                    String departmentName = input.substring(SECOND_COMMAND.length()).trim();
                    BigDecimal averageSalaryOfTheDepartment = departmentService.getAverageSalaryOfTheDepartment(departmentName);
                    System.out.println("The average salary of " + departmentName + " is " + averageSalaryOfTheDepartment);
                } else if (input.startsWith(THIRD_COMMAND)) {
                    String departmentName = input.substring(THIRD_COMMAND.length()).trim();
                    int countOfEmployeesInDepartment = departmentService.getCountOfEmployeesInDepartment(departmentName);
                    System.out.println(countOfEmployeesInDepartment);
                } else if (input.startsWith(FOURTH_COMMAND)) {
                    String template = input.substring(FOURTH_COMMAND.length()).trim();
                    String matched = departmentService.globalSearchByTemplate(template);
                    System.out.println(matched);
                } else if (input.startsWith(STATISTICS_START) && input.endsWith(STATISTICS_END)) {
                    String departmentName = input.substring(STATISTICS_START.length(), input.indexOf(STATISTICS_END)).trim();
                    String statisticOfTheDepartment = departmentService.getStatisticOfTheDepartment(departmentName);
                    System.out.println(statisticOfTheDepartment);
                } else if (input.equals(EXIT)) {
                    System.out.println("exit from application");
                    break;
                } else {
                    System.out.println("Unknown command");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
