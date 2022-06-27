package org.ariel.ApiSchoolManagement.Repository;


import org.ariel.ApiSchoolManagement.Model.Roll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRollRepository extends JpaRepository<Roll, Long> {
    public boolean existsByName(String name);
    public Roll findByName(String name); 
}
