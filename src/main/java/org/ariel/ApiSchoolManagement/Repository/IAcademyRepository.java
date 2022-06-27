package org.ariel.ApiSchoolManagement.Repository;




import org.ariel.ApiSchoolManagement.Model.Academy;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAcademyRepository extends JpaRepository<Academy, Long> {
    
    @Query( value = "SELECT * FROM academy WHERE name = :name and city = :city AND state = true", nativeQuery = true )
    public Academy findByNameAndCity(String name, String city);


    public Academy findByIdAndState(long id, boolean state);

    public boolean existsByIdAndState(long id, boolean state);

    public Academy findByHeadMasterAndState(Users headMaster, boolean state);

    @Modifying
    @Query( value = "UPDATE academy SET state = false WHERE id = :id", nativeQuery = true )
    public void deleteById(@Param("id") Long id);
}
