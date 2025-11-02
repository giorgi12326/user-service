package org.example.userservice.controller;

import lombok.AllArgsConstructor;
import org.example.userservice.dto.FullUserDTO;
import org.example.userservice.dto.TokenDTO;
import org.example.userservice.dto.UserDTO;
import org.example.userservice.security.JwtUtil;
import org.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO user) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String s = jwtUtil.generateToken(authenticate.getName());
        return ResponseEntity.status(201).body(new TokenDTO(s, "Bearer"));
    }

    @PostMapping("/register")
    public ResponseEntity<FullUserDTO> registerUser(@RequestBody FullUserDTO user) {
        FullUserDTO fullUserDTO = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(fullUserDTO);
    }

    @GetMapping
    public ResponseEntity<FullUserDTO> getUserByUsername(@RequestParam String username) {
        System.out.println("called");
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/axlesh")
    public String test() {
        return "ci-CD WORKED! first try";
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @PostMapping("/publish")
    public String publish(@RequestParam String message) {
        kafkaTemplate.send("test-topic", message);
        return "Message sent: " + message;
    }
}
