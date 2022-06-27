package org.ariel.ApiSchoolManagement.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.ariel.ApiSchoolManagement.Model.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequest {
    
    @NotNull(message = "Code is mandatory")
    private int code;

    @NotEmpty(message = "name is mandatory")
    private String name;

    @NotEmpty(message = "Email of teacher is mandatory")
    @Email(message = "field must be an email")
    private String emailTeacher;

    public Subject getSubject(){
        return new Subject(this.name, this.code);
    }
}
