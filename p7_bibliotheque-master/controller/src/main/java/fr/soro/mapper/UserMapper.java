package fr.soro.mapper;

import fr.soro.dto.UserDto;
import fr.soro.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto from(User user);

    User from(UserDto userDto);

}
