package com.itbcafrica.com.blogapplication.controller;

import com.itbcafrica.com.blogapplication.entity.Role;
import com.itbcafrica.com.blogapplication.entity.User;
import com.itbcafrica.com.blogapplication.payload.JWTAuthResponse;
import com.itbcafrica.com.blogapplication.payload.LoginDto;
import com.itbcafrica.com.blogapplication.payload.SignUpDto;
import com.itbcafrica.com.blogapplication.repository.RoleRepository;
import com.itbcafrica.com.blogapplication.repository.UserRepository;
import com.itbcafrica.com.blogapplication.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameorEmail()
                , loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // get Token tokenprovider
        String token = tokenProvider.generateToken(authenticate);


        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto) {
        // check  for username exist in a db
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        // check  for Email exist in a db
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully ", HttpStatus.OK);
    }
}

