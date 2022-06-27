package org.ariel.ApiSchoolManagement.Controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.ariel.ApiSchoolManagement.Model.Academy;
import org.ariel.ApiSchoolManagement.Request.AcademyRequest;
import org.ariel.ApiSchoolManagement.Services.AcademyService;
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
@RequestMapping("/api/academy")
@CrossOrigin
public class AcademyController {
    
    @Autowired
    private AcademyService service;

    @GetMapping("/{emailHeadMaster}")
    public ResponseEntity<Academy> getOne(@Valid @Email @PathVariable("emailHeadMaster") String email) {
        return ResponseEntity.status(200).body(service.getByHeadMaster(email));
    }

    @GetMapping("/{name}/{city}")
    public ResponseEntity<Academy> getOne(
        @Valid @PathVariable("name") String name, 
        @Valid @PathVariable("city") String city
    ) {
        return ResponseEntity.status(200).body(service.getByNameAndCity(name, city));
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody AcademyRequest request) {
        if(service.save(request.getAcademy(), request.getEmailHeadMaster())) 
            return ResponseEntity.status(200).body("Saved academy");
        return ResponseEntity.status(400).body("The academy already exists or head master doesn't exists");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        if(service.delete(id)) 
            return ResponseEntity.status(200).body("Deleted Academy");
        return ResponseEntity.status(400).body("Academy doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }
}
