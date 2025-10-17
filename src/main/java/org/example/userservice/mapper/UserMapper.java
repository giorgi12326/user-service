package org.example.userservice.mapper;


import org.example.userservice.dto.FullUserDTO;
import org.example.userservice.dto.UserDTO;
import org.example.userservice.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    @Mapping(source = "username", target = "username")
//    @Mapping(source = "password", target = "password")
    User toEntity(UserDTO userDTO);

//    @Mapping(source = "username", target = "username")
//    @Mapping(source = "password", target = "password")
//    @Mapping(source = "role", target = "role")
    User toEntity(FullUserDTO userDTO);


//    @Mapping(source = "username", target = "username")
//    @Mapping(source = "password", target = "password")
//    @Mapping(source = "role", target = "role")
    FullUserDTO toFullUserDTO(User user);

    UserDTO toDTO(User user);


}
