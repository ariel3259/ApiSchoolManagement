package org.ariel.ApiSchoolManagement.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.ariel.ApiSchoolManagement.Model.Course;
import org.ariel.ApiSchoolManagement.Request.CourseRequest;
import org.ariel.ApiSchoolManagement.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CoursesController {
    
    @Autowired
    private CourseService service;

    @GetMapping("/{academyId}")
    public ResponseEntity<List<Course>> getByAcademy(@Valid @PathVariable("academyId") long academyId){
        return ResponseEntity.status(200).body(service.getByAcademy(academyId));
    }

    @GetMapping("/{grade}/{year}/{academyId}")
    public ResponseEntity<Course> getOne(
        @Valid @PathVariable("grade") int grade, 
        @Valid @PathVariable("year") int year, 
        @Valid @PathVariable("academyId") long academyId
    ) {
        return ResponseEntity.status(200).body(service.getByGradeAndYearAndAcademy(grade, year, academyId));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody CourseRequest request){
        if(service.save(request.getCourse(), request.getSubjectsCode(), request.getStudentsEmail(), request.getIdAcademy()))
            return ResponseEntity.status(200).body("Created course");
        return ResponseEntity.status(400).body("Failed to create a course");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Valid @PathVariable("id") long id){
        if(service.delete(id)) 
            return ResponseEntity.status(200).body("Course deleted");
        return ResponseEntity.status(400).body("Failed to delete a course");
    }
}
