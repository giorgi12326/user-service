package org.example.userservice.service;

import lombok.AllArgsConstructor;
import org.example.userservice.dto.Event;
import org.example.userservice.dto.EventType;
import org.example.userservice.dto.FullUserDTO;
import org.example.userservice.entity.User;
import org.example.userservice.exception.ResourceNotFoundException;
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
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username +" not found"));
    }

    public FullUserDTO registerUser(FullUserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole())
                .build();
        User save = userRepository.save(user);
        return userMapper.toFullUserDTO(save);
    }

    public FullUserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User with username " + username +" not found"));
        return userMapper.toFullUserDTO(user);
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
