package org.ariel.ApiSchoolManagement.Controllers;



import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.ariel.ApiSchoolManagement.Model.Users;
import org.ariel.ApiSchoolManagement.Request.UsersRequest;
import org.ariel.ApiSchoolManagement.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @GetMapping("/{email}")
    public ResponseEntity<Users> getByEmail( @Valid @Email @PathVariable("email") String email) {
        return ResponseEntity.status(200).body(service.getByEmail(email));
    }

    @PostMapping("/register")
    public ResponseEntity<String> save(@Valid @RequestBody UsersRequest request) {
        if(service.save(request.getUser(), request.getRoleName()))
            return ResponseEntity.status(201).body("Created user");
        return ResponseEntity.status(400).body("User already exists");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Valid @PathVariable("id") long id) {
        if(service.delete(id)) 
            return ResponseEntity.status(200).body("Deleted user");
        return ResponseEntity.status(400).body("User doesn't exists or old deleted");
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
