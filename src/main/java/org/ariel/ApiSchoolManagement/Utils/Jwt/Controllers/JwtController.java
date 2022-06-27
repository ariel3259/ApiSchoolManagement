package org.ariel.ApiSchoolManagement.Utils.Jwt.Controllers;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.ariel.ApiSchoolManagement.Utils.Jwt.TokenManager;
import org.ariel.ApiSchoolManagement.Utils.Jwt.UsersDetailService;
import org.ariel.ApiSchoolManagement.Utils.Jwt.Model.JwtRequest;
import org.ariel.ApiSchoolManagement.Utils.Jwt.Model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class JwtController {
    
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UsersDetailService detailService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> auth(@Valid @RequestBody JwtRequest request) throws UsernameNotFoundException{
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }catch(DisabledException e){
            return ResponseEntity.status(400).body(new JwtResponse("DISABLED"));
        }catch(BadCredentialsException e){
            return ResponseEntity.status(400).body(new JwtResponse("BAD_CREDENTIALS"));
        }
        UserDetails user = detailService.loadUserByUsername(request.getEmail());
        String token = tokenManager.generateToken(user);
        return ResponseEntity.status(200).body(new JwtResponse(token));
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
