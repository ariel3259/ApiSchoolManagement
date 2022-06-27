package org.ariel.ApiSchoolManagement.Repository;



import org.ariel.ApiSchoolManagement.Model.Roll;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository extends JpaRepository<Users, Long> {
    
    public boolean existsByEmailAndState(String email, boolean state);

    public boolean existsByIdAndState(Long id, boolean state);

    public boolean existsByDniAndState(int dni, boolean state);

    @Query( value = "SELECT * FROM users WHERE email = :email AND state = true", nativeQuery = true )
    public Users findByEmail(@Param("email") String email);

    public Users findByEmailAndRoleAndState(String email, Roll role, boolean state);

    @Modifying
    @Query( value = "UPDATE users SET state = false WHERE id = :id", nativeQuery = true)
    public void deleteById(@Param("id") Long id);
}
