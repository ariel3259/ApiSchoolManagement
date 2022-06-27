package org.ariel.ApiSchoolManagement.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.sql.Date;
import org.ariel.ApiSchoolManagement.Model.Academy;
import org.ariel.ApiSchoolManagement.Model.Roll;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.ariel.ApiSchoolManagement.Repository.IAcademyRepository;
import org.ariel.ApiSchoolManagement.Repository.IRollRepository;
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
public class AcademyServiceTest{

    @InjectMocks
    private AcademyService service;

    @Mock
    private IUsersRepository usersRepository;

    @Mock
    private IRollRepository rollRepository;

    @Mock
    private IAcademyRepository academyRepository;

    private final Academy academy = new Academy("IAA", "Argentina", "Mar del plata", Date.valueOf("1916-4-13"));

    private String emailHeadMaster = "ariel@algo.com";

    private String roleName = "HEAD_MASTER";

    private Roll role = new Roll(roleName);

    private Users headMaster = new Users();

    @Before
    public void init(){
        when(rollRepository.findByName(roleName)).thenReturn(role);
    }

    @Test
    public void GET_BY_HEAD_MASTER(){
        when(usersRepository.findByEmailAndRoleAndState(emailHeadMaster, role, true)).thenReturn(headMaster);
        when(academyRepository.findByHeadMasterAndState(headMaster, true)).thenReturn(academy);
        Academy academyFound = service.getByHeadMaster(emailHeadMaster);
        assertNotNull(academyFound);
        assertEquals(academy, academyFound);
    }

    @Test
    public void GET_BY_HEAD_MASTER_BUT_HEAD_MASTER_DOES_NOT_EXISTS(){
        when(usersRepository.findByEmailAndRoleAndState(emailHeadMaster, role, true)).thenReturn(headMaster);
        Academy academyFound = service.getByHeadMaster(emailHeadMaster);
        assertNull(academyFound);
    }

    @Test
    public void GET_BY_HEAD_MASTER_BUT_ACADEMY_DOES_NOT_EXISTS(){
        when(usersRepository.findByEmailAndRoleAndState(emailHeadMaster, role, true)).thenReturn(headMaster);
        when(academyRepository.findByHeadMasterAndState(headMaster, true)).thenReturn(null);
        Academy academyFound = service.getByHeadMaster(emailHeadMaster);
        assertNull(academyFound);
    }

    @Test
    public void GET_BY_NAME_AND_CITY(){
        when(academyRepository.findByNameAndCity(academy.getName(), academy.getCity())).thenReturn(academy);
        Academy academyFound = service.getByNameAndCity(academy.getName(), academy.getCity());
        assertNotNull(academyFound);
        assertEquals(academy, academyFound);
    }

    @Test
    public void GET_BY_NAME_AND_CITY_BUT_ACADEMY_DOES_NOT_EXISTS(){
        when(academyRepository.findByNameAndCity(academy.getName(), academy.getCity())).thenReturn(null);
        Academy academyFound = service.getByNameAndCity(academy.getName(), academy.getCity());
        assertNull(academyFound);
    }

    @Test
    public void SAVE(){
        when(academyRepository.findByNameAndCity(academy.getName(), academy.getCity())).thenReturn(null);
        when(usersRepository.findByEmailAndRoleAndState(emailHeadMaster, role, true)).thenReturn(headMaster);
        when(academyRepository.save(academy)).thenReturn(academy);
        boolean result = service.save(academy, emailHeadMaster);
        assertTrue(result);
    }

    @Test
    public void SAVE_BUT_ACADEMY_ALREADY_EXISTS(){
        when(academyRepository.findByNameAndCity(academy.getName(), academy.getCity())).thenReturn(academy);
        boolean result = service.save(academy, emailHeadMaster);
        assertFalse(result);
    }

    @Test
    public void SAVE_BUT_USER_DOES_NOT_EXISTS(){
        when(usersRepository.findByEmailAndRoleAndState(emailHeadMaster, role, true)).thenReturn(null);
        boolean result = service.save(academy, emailHeadMaster);
        assertFalse(result);
    }

    @Test
    public void DELETE(){
        when(academyRepository.existsByIdAndState(academy.getId(), true)).thenReturn(true);
        boolean result = service.delete(academy.getId());
        assertTrue(result);
    }

    @Test
    public void DELETE_WITHOUT_ACADEMY(){
        when(academyRepository.existsByIdAndState(academy.getId(), true)).thenReturn(false);
        boolean result = service.delete(academy.getId());
        assertFalse(result);
    }
}