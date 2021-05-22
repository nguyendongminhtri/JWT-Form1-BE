package chinh.nguyen.demo3.controller;

import chinh.nguyen.demo3.dto.reponse.JwtResponse;
import chinh.nguyen.demo3.dto.reponse.ReponseMessage;
import chinh.nguyen.demo3.dto.request.SignInForm;
import chinh.nguyen.demo3.dto.request.SignupForm;
import chinh.nguyen.demo3.model.Role;
import chinh.nguyen.demo3.model.RoleName;
import chinh.nguyen.demo3.model.User;
import chinh.nguyen.demo3.security.jwt.JwtProvider;
import chinh.nguyen.demo3.security.userprincal.Userprinciple;
import chinh.nguyen.demo3.service.impl.RoleServiceImpl;
import chinh.nguyen.demo3.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin("*")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupForm signupForm){
        if(userService.existsByEmail(signupForm.getEmail())){
            return new ResponseEntity<>(new ReponseMessage("noemail"), HttpStatus.OK);
        }
        if(userService.existsByUsername(signupForm.getUsername())){
            return new ResponseEntity<>(new ReponseMessage("nouser"), HttpStatus.OK);
        }
        User user = new User(signupForm.getName(), signupForm.getUsername(), signupForm.getEmail(),
              passwordEncoder.encode(signupForm.getPassword()));
        Set<String> strRoles = signupForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role->{
            switch (role){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            ()-> new RuntimeException("Error -> Role not found")
                    );
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(
                            ()-> new RuntimeException("Error -> Role Not Found")
                    );
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(
                            ()-> new RuntimeException("Error -> Role Not Found")

                    );
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ReponseMessage("yes"), HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.createToken(authentication);
        Userprinciple userprinciple = (Userprinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userprinciple.getName(),
                userprinciple.getAuthorities()));
    }
}
