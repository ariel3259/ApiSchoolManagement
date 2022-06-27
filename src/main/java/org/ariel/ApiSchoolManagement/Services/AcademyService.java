package org.ariel.ApiSchoolManagement.Services;

import org.ariel.ApiSchoolManagement.Model.Academy;
import org.ariel.ApiSchoolManagement.Model.Roll;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.ariel.ApiSchoolManagement.Repository.IAcademyRepository;
import org.ariel.ApiSchoolManagement.Repository.IRollRepository;
import org.ariel.ApiSchoolManagement.Repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AcademyService {
    @Autowired
    private IAcademyRepository academyRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IRollRepository rollRepository;

    private Roll role;

    public Academy getByHeadMaster(String email){
        role = rollRepository.findByName("HEAD_MASTER");
        Users headMaster = usersRepository.findByEmailAndRoleAndState(email, role, true);
        return academyRepository.findByHeadMasterAndState(headMaster, true);   
    }

    public Academy getByNameAndCity(String name, String city){
        return academyRepository.findByNameAndCity(name, city);
    }

    public boolean save(Academy academy, String email){
        if(academyRepository.findByNameAndCity(academy.getName(), academy.getCity()) != null) return false;
        Users headMaster = usersRepository.findByEmailAndRoleAndState(email, rollRepository.findByName("HEAD_MASTER"), true);
        if(headMaster == null) return false;
        academy.setHeadMaster(headMaster);
        academyRepository.save(academy);
        return true;
    }

    public boolean delete(long id){
        if(!academyRepository.existsByIdAndState(id, true)) return false;
        academyRepository.deleteById(id);
        return true;
    }
}
