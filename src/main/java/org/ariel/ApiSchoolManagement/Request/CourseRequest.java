package org.ariel.ApiSchoolManagement.Request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.ariel.ApiSchoolManagement.Model.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {
    
    @NotNull(message = "grade is mandatory")
    private int grade;

    @NotNull(message = "year of course is mandatory")
    private int year;

    @NotEmpty(message = "Subjects code can't be 0")
    @NotNull(message = "Subjects code is mandatory")
    private Set<Integer> subjectsCode;

    @NotEmpty(message = "Students email can't be 0")
    @NotNull(message = "Students email is mandatory")
    @Email(message = "field must be an email")
    private Set<String> studentsEmail;

    @NotNull(message = "id of academy is mandatory")
    private long idAcademy;

    public Course getCourse(){
        return new Course(this.grade, this.year);
    }
}
