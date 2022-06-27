package org.ariel.ApiSchoolManagement.Repository;


import java.util.List;

import org.ariel.ApiSchoolManagement.Model.Academy;
import org.ariel.ApiSchoolManagement.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {
    
    //Find all academy courses
    public List<Course> findByAcademyAndState(Academy academy, boolean state);

    public boolean existsByYearAndGradeAndState(int year, int grade, boolean state);

    public Course findByGradeAndYearAndAcademyAndState(int grade, int year, Academy academy, boolean state);

    public boolean existsByIdAndState(long id, boolean state);

    @Modifying
    @Query( value = "UPDATE course SET state = false WHERE id = :id", nativeQuery = true )
    public void deleteById(@Param("id") Long id);
}
