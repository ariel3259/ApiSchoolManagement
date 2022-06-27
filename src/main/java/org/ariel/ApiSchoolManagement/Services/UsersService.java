package org.ariel.ApiSchoolManagement.Services;

import org.ariel.ApiSchoolManagement.Model.Roll;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.ariel.ApiSchoolManagement.Repository.IRollRepository;
import org.ariel.ApiSchoolManagement.Repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {
    
    @Autowired
    private IRollRepository rollRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * 
     * @param email
     * @return an user 
     */
    public Users getByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    /**
     * 
     * @param user The user that it'll be create
     * @param rollName The name of the role of user
     * @return a boolean, if is true, means that user was created, else return false, means that user already exists
     */
    @Transactional
    public boolean save(Users user, String rollName){
        if(
            usersRepository.existsByEmailAndState(user.getEmail(), true) ||
            usersRepository.existsByDniAndState(user.getDni(), true)    
        ) return false;
        String passwordHashed = encoder.encode(user.getPassword());
        Roll roll = rollRepository.findByName(rollName);
        user.setRole(roll);
        user.setPassword(passwordHashed);
        usersRepository.save(user);
        return true;
    }

    public boolean delete(long id){
        if(usersRepository.existsByIdAndState(id, false)) return false;
        usersRepository.deleteById(id);
        return true;
    }
}
