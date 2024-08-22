package org.example.botscrewtesttask.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.botscrewtesttask.exception.EntityNotFoundException;
import org.example.botscrewtesttask.model.Degree;
import org.example.botscrewtesttask.model.Department;
import org.example.botscrewtesttask.model.Lecturer;
import org.example.botscrewtesttask.repository.DepartmentRepository;
import org.example.botscrewtesttask.repository.LecturerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final LecturerRepository lecturerRepository;

    public String getHeadOfTheDepartment(String department_name) {
        Department department = departmentRepository.findByName(department_name)
                .orElseThrow(
                        () -> new EntityNotFoundException("No such department with name " + department_name)
                );
        return department.getHeadOfTheDepartment().getName().concat(" ").concat(department.getHeadOfTheDepartment().getSurname());
    }

    public BigDecimal getAverageSalaryOfTheDepartment(String department_name) {
        Department department = departmentRepository.findByName(department_name)
                .orElseThrow(
                        () -> new EntityNotFoundException("No such department with name " + department_name)
                );
        return BigDecimal.valueOf(department.getLecturers().stream()
                .mapToDouble(Lecturer::getSalary)
                .average()
                .getAsDouble()
        );
    }

    public int getCountOfEmployeesInDepartment(String department_name) {
        Department department = departmentRepository.findByName(department_name)
                .orElseThrow(
                        () -> new EntityNotFoundException("No such department with name " + department_name)
                );
        return department.getLecturers().size();
    }

    public String globalSearchByTemplate(String template) {
        List<String> matchesWithTemplate = lecturerRepository.findByNameTemplate(template);
        if (matchesWithTemplate.isEmpty()) {
            throw new EntityNotFoundException("No such lecturer with template " + template);
        }

        return String.join(", ", matchesWithTemplate);
    }

    public String getStatisticOfTheDepartment(String department_name) {
        Department department = departmentRepository.findByName(department_name)
                .orElseThrow(
                        () -> new EntityNotFoundException("No such department with name " + department_name)
                );
        Set<Lecturer> lecturers = department.getLecturers();
        if (lecturers.isEmpty()) {
            return "No lecturers in department with name " + department_name;
        }
        long professorsCount = lecturers.stream()
                .filter(l -> l.getDegree() == Degree.PROFESSOR)
                .count();
        long assistantCount = lecturers.stream()
                .filter(l -> l.getDegree() == Degree.ASSISTANT)
                .count();
        long associateProfessorCount = lecturers.stream()
                .filter(l -> l.getDegree() == Degree.ASSOCIATE_PROFESSOR)
                .count();

        return String.format("assistants - %d\nassociate professors - %d\nprofessors - %d", assistantCount, associateProfessorCount, professorsCount);
    }
}
