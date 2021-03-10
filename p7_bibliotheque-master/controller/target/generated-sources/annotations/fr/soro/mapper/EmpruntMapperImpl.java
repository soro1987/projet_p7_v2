package fr.soro.mapper;

import fr.soro.dto.EmpruntDto;
import fr.soro.entities.Emprunt;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-09T20:24:12+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_282 (Private Build)"
)
@Component
public class EmpruntMapperImpl implements EmpruntMapper {

    @Override
    public EmpruntDto from(Emprunt emprunt) {
        if ( emprunt == null ) {
            return null;
        }

        EmpruntDto empruntDto = new EmpruntDto();

        return empruntDto;
    }

    @Override
    public Emprunt from(EmpruntDto empruntDto) {
        if ( empruntDto == null ) {
            return null;
        }

        Emprunt emprunt = new Emprunt();

        return emprunt;
    }
}
