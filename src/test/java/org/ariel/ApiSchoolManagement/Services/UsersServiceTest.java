package org.ariel.ApiSchoolManagement.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.ariel.ApiSchoolManagement.Model.Roll;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.ariel.ApiSchoolManagement.Repository.IRollRepository;
import org.ariel.ApiSchoolManagement.Repository.IUsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTest {
    
    @InjectMocks
    private UsersService service;

    @Mock
    private IUsersRepository  usersRepository;

    @Mock
    private IRollRepository rollRepository;

    @Mock
    private PasswordEncoder encoder;

    private final Users user = new Users("Ariel Santangelo", "arielS@gmail.com", "1234", "AR", 44222333);

    private final String role = new String();

    @Test
    public void GET_BY_EMAIL_NOT_NULL(){
        when(usersRepository.findByEmail(user.getEmail())).thenReturn(user);
        Users userFound = service.getByEmail(user.getEmail());
        assertNotNull(userFound);
        assertEquals(userFound, user); 
    }

    @Test
    public void GET_BY_EMAIL_NULL(){
        when(usersRepository.findByEmail(user.getEmail())).thenReturn(null);
        Users userFound = service.getByEmail(user.getEmail());
        assertNull(userFound);
    }

    @Test
    public void SAVE(){
        when(usersRepository.existsByEmailAndState(user.getEmail(), true)).thenReturn(false);
        when(usersRepository.existsByDniAndState(user.getDni(), true)).thenReturn(false);
        when(encoder.encode(user.getPassword())).thenReturn(new String());
        when(rollRepository.findByName(role)).thenReturn(new Roll(role));
        when(usersRepository.save(user)).thenReturn(user);
        boolean result = service.save(user, role);
        assertTrue(result);
    }

    @Test 
    public void SAVE_WITH_AN_EXISTING_EMAIL(){
        when(usersRepository.existsByEmailAndState(user.getEmail(), true)).thenReturn(true);
        boolean result = service.save(user, role);
        assertFalse(result);
    }

    @Test
    public void SAVE_WITH_AN_EXISTING_DNI(){
        when(usersRepository.existsByDniAndState(user.getDni(), true)).thenReturn(true);
        boolean result = service.save(user, role);
        assertFalse(result);
    }

    @Test
    public void SAVE_WITH_EXISTING_DNI_AND_EMAIL(){
        when(usersRepository.existsByEmailAndState(user.getEmail(), true)).thenReturn(true);
        when(usersRepository.existsByDniAndState(user.getDni(), true)).thenReturn(true);
        boolean result = service.save(user, role);
        assertFalse(result);
    }


    @Test
    public void DELETE(){
        when(usersRepository.existsByIdAndState(user.getId(), false)).thenReturn(false);
        boolean result = service.delete(user.getId());
        assertTrue(result);
    }

    @Test
    public void DELETE_WITH_USER_DELETED(){
        when(usersRepository.existsByIdAndState(user.getId(), false)).thenReturn(true);
        boolean result = service.delete(user.getId());
        assertFalse(result);
    }
}
