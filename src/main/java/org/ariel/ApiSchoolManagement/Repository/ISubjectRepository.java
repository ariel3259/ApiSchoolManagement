package org.ariel.ApiSchoolManagement.Repository;


import org.ariel.ApiSchoolManagement.Model.Subject;
import org.ariel.ApiSchoolManagement.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, Long> {
    
    public Page<Subject> findByState(boolean state, Pageable pageable);

    public boolean existsByIdAndState(long id, boolean state);

    public boolean existsByNameAndTeacherAndState(String name, Users teacher, boolean state);

    public boolean existsByCodeAndState(int code, boolean state);

    
    public Subject findByNameAndTeacherAndState( String name,  Users teacher, boolean state);

    @Query( value = "SELECT * FROM subject WHERE code = :code AND state = true", nativeQuery = true )
    public Subject findByCode(@Param("code") int code);

    @Query
    public Subject findById(int id);

    public Subject findByTeacherAndState(Users teacher, boolean state);

    @Modifying
    @Query( value = "UPDATE academy SET state = false WHERE id = :id", nativeQuery = true )
    public void deleteById(@Param("id") Long id);
}
