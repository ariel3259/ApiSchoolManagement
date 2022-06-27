package org.ariel.ApiSchoolManagement.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
        @UniqueConstraint(columnNames = {"name"})
    }
)
public class Roll {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    public Roll(String name){
        this.name = name;
    }
}
