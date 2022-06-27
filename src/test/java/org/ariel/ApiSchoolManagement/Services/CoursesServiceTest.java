package org.ariel.ApiSchoolManagement.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ariel.ApiSchoolManagement.Model.Academy;
import org.ariel.ApiSchoolManagement.Model.Course;
import org.ariel.ApiSchoolManagement.Model.Roll;
import org.ariel.ApiSchoolManagement.Model.Subject;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.ariel.ApiSchoolManagement.Repository.IAcademyRepository;
import org.ariel.ApiSchoolManagement.Repository.ICourseRepository;
import org.ariel.ApiSchoolManagement.Repository.IRollRepository;
import org.ariel.ApiSchoolManagement.Repository.ISubjectRepository;
import org.ariel.ApiSchoolManagement.Repository.IUsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CoursesServiceTest {
    
    @InjectMocks
    private CourseService service;

    @Mock
    private IUsersRepository usersRepository;

    @Mock
    private IRollRepository rollRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IAcademyRepository academyRepository;

    @Mock
    private ISubjectRepository subjectRepository;

    private final Course course = new Course(7, 2022);

    private final List<Course> courses = new ArrayList<>();

    private final Academy academy = new Academy("IAA", "Argentina", "Mar del plata", Date.valueOf("1916-4-13"));

    private final Subject subject =  new Subject("Math", 3245);

    private final Users student = new Users();

    private final Set<Subject> subjects = new HashSet<>();

    private final Set<Users> students = new HashSet<>();

    private final String roleName = "STUDENT";

    private final Roll  role = new Roll(roleName);

    @Before
    public void init(){
        when(rollRepository.findByName(roleName)).thenReturn(role); 
        courses.add(course);
        students.add(student);
        subjects.add(subject);
    }
 
    @Test
    public void GET_BY_ACADEMY(){
        when(academyRepository.findByIdAndState(academy.getId(), true)).thenReturn(academy);
        when(courseRepository.findByAcademyAndState(academy, true)).thenReturn(courses);
        List<Course> coursesFound = service.getByAcademy(academy.getId());
        assertEquals(courses, coursesFound);
    }

    @Test
    public void GET_BY_ACADEMY_BUT_ACADEMY_DOES_NOT_EXISTS(){
        when(academyRepository.findByIdAndState(academy.getId(), true)).thenReturn(null);
        List<Course> coursesFound = service.getByAcademy(academy.getId());
        assertNotEquals(courses, coursesFound);
    }

    @Test
    public void GET_BY_ACADEMY_BUT_COURSES_ARE_NULL(){
        when(academyRepository.findByIdAndState(academy.getId(), true)).thenReturn(academy);
        when(courseRepository.findByAcademyAndState(academy, true)).thenReturn(new ArrayList<>());
        List<Course> coursesFound = service.getByAcademy(academy.getId());
        assertNotEquals(courses, coursesFound);
    }

    @Test
    public void GET_BY_GRADE_YEAR_ACADEMY(){
        when(academyRepository.findByIdAndState(academy.getId(), true)).thenReturn(academy);
        when(courseRepository.findByGradeAndYearAndAcademyAndState(course.getGrade(), course.getYear(), academy, true)).thenReturn(course);
        Course courseFound = service.getByGradeAndYearAndAcademy(course.getGrade(), course.getYear(), academy.getId());
        assertNotNull(courseFound);
        assertEquals(course, courseFound);
    }

    @Test
    public void DELETE(){
        when(courseRepository.existsByIdAndState(course.getId(), false)).thenReturn(false);
        boolean result = service.delete(course.getId());
        assertTrue(result);
    }

    @Test
    public void DELETE_BUT_COURSE_DOES_NOT_EXISTS(){
        when(courseRepository.existsByIdAndState(course.getId(), false)).thenReturn(true);
        boolean result = service.delete(course.getId());
        assertFalse(result);
    }
}
