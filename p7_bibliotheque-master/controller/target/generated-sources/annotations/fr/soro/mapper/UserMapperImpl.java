package fr.soro.mapper;

import fr.soro.dto.UserDto;
import fr.soro.entities.User;
import fr.soro.entities.User.UserBuilder;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-09T20:24:12+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_282 (Private Build)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto from(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setNom( user.getNom() );
        userDto.setPrenom( user.getPrenom() );
        userDto.setEmail( user.getEmail() );
        userDto.setTelephone( user.getTelephone() );
        userDto.setUser_active( user.getUser_active() );
        userDto.setPassword( user.getPassword() );
        userDto.setUsername( user.getUsername() );

        return userDto;
    }

    @Override
    public User from(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.nom( userDto.getNom() );
        user.prenom( userDto.getPrenom() );
        user.email( userDto.getEmail() );
        user.telephone( userDto.getTelephone() );
        user.user_active( userDto.getUser_active() );
        user.username( userDto.getUsername() );
        user.password( userDto.getPassword() );

        return user.build();
    }
}
