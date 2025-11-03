package org.example.userservice.mapper;

import javax.annotation.processing.Generated;
import org.example.userservice.dto.FullUserDTO;
import org.example.userservice.dto.UserDTO;
import org.example.userservice.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-03T13:30:43+0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userDTO.getUsername() );
        user.password( userDTO.getPassword() );

        return user.build();
    }

    @Override
    public User toEntity(FullUserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userDTO.getUsername() );
        user.password( userDTO.getPassword() );
        user.role( userDTO.getRole() );

        return user.build();
    }

    @Override
    public FullUserDTO toFullUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        FullUserDTO fullUserDTO = new FullUserDTO();

        fullUserDTO.setUsername( user.getUsername() );
        fullUserDTO.setPassword( user.getPassword() );
        fullUserDTO.setRole( user.getRole() );

        return fullUserDTO;
    }

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername( user.getUsername() );
        userDTO.setPassword( user.getPassword() );

        return userDTO;
    }
}
