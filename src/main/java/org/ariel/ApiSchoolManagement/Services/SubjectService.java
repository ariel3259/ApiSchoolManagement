package org.ariel.ApiSchoolManagement.Services;



import org.ariel.ApiSchoolManagement.Model.Roll;
import org.ariel.ApiSchoolManagement.Model.Subject;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.ariel.ApiSchoolManagement.Repository.IRollRepository;
import org.ariel.ApiSchoolManagement.Repository.ISubjectRepository;
import org.ariel.ApiSchoolManagement.Repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    private IUsersRepository userRepository;
    
    @Autowired
    private ISubjectRepository subjectRepository;

    @Autowired
    private IRollRepository rollRepository;

    private  Roll roleTeacher;

    public Subject getByNameAndTeacher(String name, String emailTeacher){
        roleTeacher = rollRepository.findByName("TEACHER");
        Users teacher = userRepository.findByEmailAndRoleAndState(emailTeacher, roleTeacher, true);
        return subjectRepository.findByNameAndTeacherAndState(name, teacher, true);
    }

    public Subject getByCode(int code){
        return subjectRepository.findByCode(code);
    }

    public Subject getByTeacher(String emailTeacher){
        roleTeacher = rollRepository.findByName("TEACHER");
        Users teacher = userRepository.findByEmailAndRoleAndState(emailTeacher, roleTeacher, true);
        return subjectRepository.findByTeacherAndState(teacher, true);
    }

    public boolean save(Subject subject, String emailTeacher){
        roleTeacher = rollRepository.findByName("TEACHER");
        Users teacher = userRepository.findByEmailAndRoleAndState(emailTeacher, roleTeacher, true);

        if(
            teacher == null ||
            subjectRepository.existsByCodeAndState(subject.getCode(), true) || 
            subjectRepository.existsByNameAndTeacherAndState(subject.getName(), teacher, true)
        ) return false;

        subject.setTeacher(teacher);
        subjectRepository.save(subject);
        return true;
    }

    public boolean delete(long id){
        if(subjectRepository.existsByIdAndState(id, false)) return false;
        subjectRepository.deleteById(id);
        return true;
    }
}
