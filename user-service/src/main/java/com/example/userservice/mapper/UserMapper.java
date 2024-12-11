package com.example.userservice.mapper;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(UserDto userDto);
    UserDto toUserDto(UserEntity userEntity);
    UserDto requestToUserDto(RequestUser requestUser);
    ResponseUser dtoToResponseUser(UserDto userDto);
    ResponseUser toResponseUser(UserEntity userEntity);
}
