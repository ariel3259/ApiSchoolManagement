package org.ariel.ApiSchoolManagement.Model;

import javax.persistence.CascadeType;
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
        @UniqueConstraint(columnNames = {"code"})
    }
)
public class Subject {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String name;
    
    private int code;

    @OneToOne( cascade = CascadeType.REMOVE)
    private Users teacher;
    
    private boolean state;
    
    public Subject(String name, int code){
        this.name = name;
        this.code = code;
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
