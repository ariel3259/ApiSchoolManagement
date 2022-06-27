package org.ariel.ApiSchoolManagement.Utils.Jwt.Model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
    @NotEmpty(message = "email is mandatory")
    @Email(message = "field must be an email")
    private String email;
    @NotEmpty(message = "password is mandatory")
    private String password;
}
