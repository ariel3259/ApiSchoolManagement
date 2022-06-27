package org.ariel.ApiSchoolManagement.Services;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    
    @Autowired
    private IAcademyRepository academyRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private ISubjectRepository subjectRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private IRollRepository rollRepository;

    /**
     * 
     * @param idAcademy the field to find its courses
     * @return a list of courses
     */
    public List<Course> getByAcademy(long idAcademy){
        Academy academy = academyRepository.findByIdAndState(idAcademy, true);
        return courseRepository.findByAcademyAndState(academy, true);
    }

    /**
     * 
     * @param grade of course
     * @param year of course
     * @param idAcademy of course
     * @return the specific course
     */
    public Course getByGradeAndYearAndAcademy(int grade, int year, long idAcademy){
        Academy academy = academyRepository.findByIdAndState(idAcademy, true);
        return courseRepository.findByGradeAndYearAndAcademyAndState(grade, year, academy, true);
    }
    
    /**
     * 
     * @param course that will be saved
     * @param codesSubject the subject codes of the course
     * @param emailStudents the emails student oof the course
     * @param idAcademy the 
     * @return a boolean to determinates if save it or not
     */
    public boolean save(Course course, Set<Integer> codesSubject, Set<String> emailsStudent, long idAcademy){
        //validates if exists The courses or not
        if(
            courseRepository.existsByYearAndGradeAndState(course.getYear(), course.getGrade(), true)
        ) return false;

        Roll role = rollRepository.findByName("STUDENT");

        //students and subjects sets
        Set<Subject> subjects = new HashSet<>();
        Set<Users> students = new HashSet<>();

        
        //finding subjects for courses 
        for(int code: codesSubject){
            if(subjectRepository.findByCode(code) != null) subjects.add(subjectRepository.findByCode(code)); 
        }

        //finding students for courses
        for(String email: emailsStudent){
            if (
                usersRepository.findByEmailAndRoleAndState(email, role, true) != null
            ) {
                students.add(
                    usersRepository.findByEmailAndRoleAndState(email, role, true)
                );
            }  
        }

        //find academy for courses
        Academy academy = academyRepository.findById(idAcademy).get();
        
        //validates if there're subjects, there're students and there's academy
        if(subjects.isEmpty() || students.isEmpty() || academy == null) return false;

        //setting students, subjects and academy to course
        course.setAcademy(academy);
        course.setStudents(students);
        course.setSubjects(subjects);

        //persisting course
        courseRepository.save(course);
        return true;
    }

    public boolean delete(long id){
        if(courseRepository.existsByIdAndState(id, false)) return false;
        courseRepository.deleteById(id);
        return true;
    }
}
