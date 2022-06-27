package org.ariel.ApiSchoolManagement.Model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
        @UniqueConstraint(columnNames = {"grade", "year"})
    }
)
public class Course {    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int grade;

    private int year;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Academy academy;

    @OneToMany( cascade = CascadeType.REMOVE)
    private Set<Subject> subjects;

    @OneToMany( cascade = CascadeType.REMOVE)
    private Set<Users> students; 

    private boolean state;
    
    public Course(int grade, int year){
        this.grade = grade;
        this.year = year;
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
