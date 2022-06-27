package org.ariel.ApiSchoolManagement.Controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.ariel.ApiSchoolManagement.Model.Subject;
import org.ariel.ApiSchoolManagement.Request.SubjectRequest;
import org.ariel.ApiSchoolManagement.Services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/subjects")
@CrossOrigin
public class SubjectController {
    
    @Autowired
    private SubjectService service;

    @GetMapping("/{name}/{emailTeacher}")
    public ResponseEntity<Subject> getOne(
        @Valid @PathVariable("name") String name, 
        @Valid @Email @PathVariable("emaiLTeacher") String emailTeacher
    ) {
        return ResponseEntity.status(200).body(service.getByNameAndTeacher(name, emailTeacher));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Subject> getOne(@Valid @PathVariable("code") int code) {
        return ResponseEntity.status(200).body(service.getByCode(code));
    }
    
    @GetMapping("/{emailTeacher}")
    public ResponseEntity<Subject> getOne( @Valid @Email @PathVariable("emailTeacher") String emailTeacher) {
        return ResponseEntity.status(200).body(service.getByTeacher(emailTeacher));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody SubjectRequest request) {
        if(service.save(request.getSubject(), request.getEmailTeacher()))
            return ResponseEntity.status(201).body("Created subject");
        return ResponseEntity.status(400).body("Subject already exists or teacher doesn't exists");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Valid @PathVariable("id") long id){
        if(service.delete(id)) 
            return ResponseEntity.status(200).body("Deleted subject");
        return ResponseEntity.status(400).body("Subject doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }
}
