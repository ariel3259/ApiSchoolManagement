package org.ariel.ApiSchoolManagement.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.ariel.ApiSchoolManagement.Model.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersRequest {

    @NotEmpty(message = "Full name is mandatory")
    private String fullName;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "field must be an email")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    private String password;

    @NotEmpty(message = "Country is mandatory")
    private String country;

    @NotNull(message = "Dni is mandatory")
    private int dni;
    
    @NotEmpty(message = "Name of role is mandatory")
    private String roleName;

    public Users getUser(){
        return new Users(this.fullName, this.email, this.password, this.country, this.dni);
    }
}
