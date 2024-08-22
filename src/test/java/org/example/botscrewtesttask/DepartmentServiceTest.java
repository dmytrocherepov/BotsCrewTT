package org.example.botscrewtesttask;

import org.example.botscrewtesttask.exception.EntityNotFoundException;
import org.example.botscrewtesttask.model.Degree;
import org.example.botscrewtesttask.model.Department;
import org.example.botscrewtesttask.model.Lecturer;
import org.example.botscrewtesttask.repository.DepartmentRepository;
import org.example.botscrewtesttask.repository.LecturerRepository;
import org.example.botscrewtesttask.service.DepartmentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private LecturerRepository lecturerRepository;
    @InjectMocks
    private DepartmentService departmentService;

    @Test
    @Description("get head of the department")
    public void getHeadOfTheDepartment_validData() {
        Lecturer lecturer = new Lecturer();
        lecturer.setName("Jhon");
        lecturer.setSurname("Paul");
        lecturer.setSalary(1000.);
        lecturer.setDegree(Degree.PROFESSOR);

        Department department = new Department();
        department.setName("FirstDep");
        department.setHeadOfTheDepartment(lecturer);
        department.setLecturers(Set.of(lecturer));

        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.of(department));

        String headOfTheDepartment = departmentService.getHeadOfTheDepartment(department.getName());

        assertEquals("Jhon Paul", headOfTheDepartment);
    }


    @Test
    @Description("Get average salary for specific department")
    public void getAverageSalary_validData() {
        Lecturer lecturer = new Lecturer();
        lecturer.setName("Jhon");
        lecturer.setSurname("Paul");
        lecturer.setSalary(1000.);
        lecturer.setDegree(Degree.PROFESSOR);

        Lecturer lecturer2 = new Lecturer();
        lecturer2.setName("Bob");
        lecturer2.setSurname("Daniels");
        lecturer2.setSalary(2000.);
        lecturer2.setDegree(Degree.ASSOCIATE_PROFESSOR);

        Department department = new Department();
        department.setName("FirstDep");
        department.setHeadOfTheDepartment(lecturer);
        department.setLecturers(Set.of(lecturer, lecturer2));

        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.of(department));

        BigDecimal averageSalaryOfTheDepartment = departmentService.getAverageSalaryOfTheDepartment(department.getName());

        assertEquals(BigDecimal.valueOf(1500.), averageSalaryOfTheDepartment);
    }

    @Test
    @Description("Get count of employees in the department")
    public void getCount_validData() {
        Lecturer lecturer = new Lecturer();
        lecturer.setName("Jhon");
        lecturer.setSurname("Paul");
        lecturer.setSalary(1000.);
        lecturer.setDegree(Degree.PROFESSOR);

        Lecturer lecturer2 = new Lecturer();
        lecturer2.setName("Bob");
        lecturer2.setSurname("Daniels");
        lecturer2.setSalary(2000.);
        lecturer2.setDegree(Degree.ASSOCIATE_PROFESSOR);

        Department department = new Department();
        department.setName("FirstDep");
        department.setHeadOfTheDepartment(lecturer);
        department.setLecturers(Set.of(lecturer, lecturer2));

        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.of(department));

        int countOfEmployeesInDepartment = departmentService.getCountOfEmployeesInDepartment(department.getName());

        assertEquals(department.getLecturers().size(), countOfEmployeesInDepartment);
    }

    @Test
    @Description("Find employees by template")
    public void findByTemplate_validData() {
        String template = "ie";

        List<String> matches = List.of("Bob Daniels");

        when(lecturerRepository.findByNameTemplate(template)).thenReturn(matches);

        String res = departmentService.globalSearchByTemplate(template);

        assertEquals("Bob Daniels", res);
    }

    @Test
    @Description("Get statistic of the department")
    public void getStatistic_validData() {
        Lecturer lecturer = new Lecturer();
        lecturer.setName("Jhon");
        lecturer.setSurname("Paul");
        lecturer.setSalary(1000.);
        lecturer.setDegree(Degree.PROFESSOR);

        Lecturer lecturer2 = new Lecturer();
        lecturer2.setName("Bob");
        lecturer2.setSurname("Daniels");
        lecturer2.setSalary(2000.);
        lecturer2.setDegree(Degree.ASSOCIATE_PROFESSOR);

        Set<Lecturer> lecturers = new HashSet<>();
        lecturers.add(lecturer);
        lecturers.add(lecturer2);

        Department department = new Department();
        department.setName("FirstDep");
        department.setHeadOfTheDepartment(lecturer);
        department.setLecturers(Set.of(lecturer, lecturer2));

        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.of(department));

        String statisticOfTheDepartment = departmentService.getStatisticOfTheDepartment(department.getName());
        assertEquals("assistants - 0\nassociate professors - 1\nprofessors - 1", statisticOfTheDepartment);
    }

    @Test
    @Description("get head of the department")
    public void getHeadOfTheDepartment_invalidData_ShouldThrowException() {
        Department department = new Department();
        department.setName("FirstDep");

        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> departmentService.getHeadOfTheDepartment(department.getName()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No such department with name " + department.getName());
    }

    @Test
    @Description("Find employees by template")
    public void findByTemplate_invalidData_shouldThrowException() {
        String template = "ie";

        List<String> matches = List.of();

        when(lecturerRepository.findByNameTemplate(template)).thenReturn(matches);

        assertThatThrownBy(() -> departmentService.globalSearchByTemplate(template))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No such lecturer with template " + template);
    }

}
