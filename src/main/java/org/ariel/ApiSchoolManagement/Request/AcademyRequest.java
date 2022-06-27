package org.ariel.ApiSchoolManagement.Request;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.ariel.ApiSchoolManagement.Model.Academy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademyRequest {
    
    @NotEmpty(message = "Name of school is mandatory")
    private String name;
    
    @NotEmpty(message = "Country is mandatory")
    private String country;
    
    @NotEmpty(message = "City is mandatory")
    private String city;

    @NotEmpty(message = "Date of building is mandatory")
    private Date dateOfBuilding;

    @NotEmpty(message = "Email of teacher is mandatory")
    @Email(message = "field must be an email")
    private String emailHeadMaster;

    public Academy getAcademy(){
        return new Academy(this.name, this.country, this.city, this.dateOfBuilding);
    }
}
