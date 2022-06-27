package org.ariel.ApiSchoolManagement.Model;

import java.sql.Date;

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
        @UniqueConstraint(columnNames = {"name", "city"})
    }
)
public class Academy {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String name;
    
    private String country;
    
    private String city;

    private Date dateOfBuilding;

    @OneToOne
    private Users headMaster;

    private boolean state;

    public Academy(String name, String country, String city, Date dateOfBuilding){
        this.name = name;
        this.country = country;
        this.city = city;
        this.dateOfBuilding = dateOfBuilding;
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
