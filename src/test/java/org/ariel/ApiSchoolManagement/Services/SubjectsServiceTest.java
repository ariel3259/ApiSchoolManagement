package org.ariel.ApiSchoolManagement.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.ariel.ApiSchoolManagement.Model.Roll;
import org.ariel.ApiSchoolManagement.Model.Subject;
import org.ariel.ApiSchoolManagement.Model.Users;
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
public class SubjectsServiceTest {
    
    @InjectMocks
    private SubjectService service;

    @Mock
    private IRollRepository rollRepository;

    @Mock
    private IUsersRepository usersRepository;

    @Mock 
    private ISubjectRepository subjectRepository;

    private final String roleName = "TEACHER";

    private final Roll role = new Roll(roleName);

    private final String emailTeacher = new String();

    private final Users teacher = new Users();

    private final Subject subject = new Subject("Math", 3245);

    @Before
    public void init(){
        when(rollRepository.findByName(roleName)).thenReturn(role);
    }

    @Test
    public void GET_BY_NAME_AND_TEACHER_NOT_NULL(){
        when(usersRepository.findByEmailAndRoleAndState(emailTeacher, role, true)).thenReturn(teacher);
        when(subjectRepository.findByNameAndTeacherAndState(subject.getName(), teacher, true)).thenReturn(subject);
        Subject subjectFound = service.getByNameAndTeacher(subject.getName(), emailTeacher);
        assertNotNull(subjectFound);
        assertEquals(subject, subjectFound);
    }

    @Test
    public void GET_BY_NAME_AND_TEACHER_WITH_NULL_USER(){
        when(usersRepository.findByEmailAndRoleAndState(emailTeacher, role, true)).thenReturn(null);
        Subject subjectFound = service.getByNameAndTeacher(subject.getName(), emailTeacher);
        assertNull(subjectFound);
    }

    @Test 
    public void GET_BY_NAME_AND_TEACHER_WITH_NULL_SUBJECT(){
        when(usersRepository.findByEmailAndRoleAndState(emailTeacher, role, true)).thenReturn(teacher);
        when(subjectRepository.findByNameAndTeacherAndState(subject.getName(), teacher, true)).thenReturn(null);
        Subject subjectFound = service.getByNameAndTeacher(subject.getName(), emailTeacher);
        assertNull(subjectFound);
    }

    @Test
    public void GET_BY_CODE_NOT_NULL(){
        when(subjectRepository.findByCode(subject.getCode())).thenReturn(subject);
        Subject subjectFound = service.getByCode(subject.getCode());
        assertNotNull(subjectFound);
        assertEquals(subject, subjectFound);
    }

    @Test
    public void GET_BY_CODE_NULL(){
        when(subjectRepository.findByCode(subject.getCode())).thenReturn(null);
        Subject subjectFound = service.getByCode(subject.getCode());
        assertNull(subjectFound);
    }

    @Test
    public void GET_BY_TEACHER(){
        when(usersRepository.findByEmailAndRoleAndState(emailTeacher, role, true)).thenReturn(teacher);
        when(subjectRepository.findByTeacherAndState(teacher, true)).thenReturn(subject);
        Subject subjectFound = service.getByTeacher(emailTeacher);
        assertNotNull(subjectFound);
        assertEquals(subject, subjectFound);
    }

    @Test
    public void GET_BY_TEACHER_BUT_TEACHER_IS_NULL(){
        when(usersRepository.findByEmailAndRoleAndState(emailTeacher, role, true)).thenReturn(null);
        Subject subjectFound = service.getByTeacher(emailTeacher);
        assertNull(subjectFound);
    }

    @Test
    public void GET_BY_TEACHER_BUT_SUBJECT_IS_NULL(){
        when(usersRepository.findByEmailAndRoleAndState(emailTeacher, role, true)).thenReturn(teacher);
        when(subjectRepository.findByTeacherAndState(teacher, true)).thenReturn(null);
        Subject subjectFound = service.getByTeacher(emailTeacher);
        assertNull(subjectFound);
    }

    @Test
    public void SAVE(){
        when(usersRepository.findByEmailAndRoleAndState(emailTeacher, role, true)).thenReturn(teacher);
        when(subjectRepository.existsByCodeAndState(subject.getCode(), true)).thenReturn(false);
        when(subjectRepository.existsByNameAndTeacherAndState(subject.getName(), teacher, true)).thenReturn(false);
        when(subjectRepository.save(subject)).thenReturn(subject);
        boolean result = service.save(subject, emailTeacher);
        assertTrue(result);
    }

    @Test
    public void SAVE_WITHOUT_TEACHER(){
        when(usersRepository.findByEmailAndRoleAndState(emailTeacher, role, true)).thenReturn(null);
        boolean result = service.save(subject, emailTeacher);
        assertFalse(result);
    }  

    @Test
    public void SAVE_WITH_AN_EXISTING_CODE(){
        when(subjectRepository.existsByCodeAndState(subject.getCode(), true)).thenReturn(true);
        boolean result = service.save(subject, emailTeacher);
        assertFalse(result);
    }

    @Test
    public void SAVE_WITH_AN_EXISTING_TEACHER_AND_NAME(){
        when(usersRepository.findByEmailAndRoleAndState(emailTeacher, role, true)).thenReturn(teacher);
        when(subjectRepository.existsByNameAndTeacherAndState(subject.getName(), teacher, true)).thenReturn(true);
        boolean result = service.save(subject, emailTeacher);
        assertFalse(result);
    }

    @Test
    public void DELETE(){
        when(subjectRepository.existsByIdAndState(subject.getId(), false)).thenReturn(false);
        boolean result = service.delete(subject.getId());
        assertTrue(result);
    }

    @Test
    public void DELETE_WITH_AN_DELETED_USER(){
        when(subjectRepository.existsByIdAndState(subject.getId(), false)).thenReturn(true);
        boolean result = service.delete(subject.getId());
        assertFalse(result);
    }
}
