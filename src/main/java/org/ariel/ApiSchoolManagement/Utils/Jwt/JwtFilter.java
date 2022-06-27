package org.ariel.ApiSchoolManagement.Utils.Jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UsersDetailService detailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String rawToken = request.getHeader("authorization");
        String username = null;
        String token = null;
        UserDetails user;

        if (rawToken != null && rawToken.startsWith("Bearer ")){
            token = rawToken.substring(7);
            try{
                username = tokenManager.getUsernameFromToken(token);
            }
            catch(IllegalArgumentException e){
                System.out.println("EMPTY OKEN");
            }
            catch(ExpiredJwtException e){
                System.out.println("EXPIRED TOKEN");
            }
            catch(SignatureException e){
                System.out.println("WRONG TOKEN");
            }
        }
        else System.out.println("TOKEN BEARER NOT FOUND");

        if(username != null  && SecurityContextHolder.getContext().getAuthentication() == null){
            user = detailService.loadUserByUsername(username);
            if(tokenManager.validateToken(user, token)){
                UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                userAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userAuth);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
