package ru.aleksey.NauJava.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aleksey.NauJava.dtos.UserDto;
import ru.aleksey.NauJava.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto mapToDto(User user);
}
