package org.ariel.ApiSchoolManagement.Utils.Jwt.Model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtResponse implements Serializable {
    private String value;
}
