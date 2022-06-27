package org.ariel.ApiSchoolManagement.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dni"}),
        @UniqueConstraint(columnNames = {"email"})
    }
)
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullName;

    private String email;

    private String password;

    private String country;

    private int dni;
    
    @OneToOne
    private Roll role;

    private boolean state;
    
    public Users(String fullName, String email, String password, String country, int dni){
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.country = country;
        this.dni = dni;
        this.state = true;
    }

    @PrePersist
    public void preInsert(){
        state = true;
    }

    @PreUpdate
    public void preUpdate(){
        state = true;
    }
}
