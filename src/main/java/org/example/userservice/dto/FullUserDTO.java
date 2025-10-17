package org.example.userservice.dto;

import lombok.Data;
import org.example.userservice.entity.Role;

@Data
public class FullUserDTO {
    private String username;
    private String password;
    private Role role;
}
