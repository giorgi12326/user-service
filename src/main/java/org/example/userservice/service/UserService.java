package org.example.userservice.service;

import lombok.AllArgsConstructor;
import org.example.userservice.dto.Event;
import org.example.userservice.dto.EventType;
import org.example.userservice.dto.FullUserDTO;
import org.example.userservice.entity.User;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.repository.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public FullUserDTO registerUser(FullUserDTO user) {
        User savedUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build();
        userRepository.save(savedUser);
        return user;
    }

    public FullUserDTO getUserByUsername(String username) {
        return userMapper.toFullUserDTO(userRepository.findByUsername(username));
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
        Event event = new Event(EventType.DELETED, Instant.now(), username);
        kafkaTemplate.send("user-event", event);
    }
}
