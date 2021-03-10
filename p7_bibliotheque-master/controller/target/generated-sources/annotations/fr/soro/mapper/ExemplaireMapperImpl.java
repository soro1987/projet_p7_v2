package fr.soro.mapper;

import fr.soro.dto.ExemplaireDto;
import fr.soro.entities.Exemplaire;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-09T20:24:12+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_282 (Private Build)"
)
@Component
public class ExemplaireMapperImpl implements ExemplaireMapper {

    @Override
    public ExemplaireDto from(Exemplaire exemplaire) {
        if ( exemplaire == null ) {
            return null;
        }

        ExemplaireDto exemplaireDto = new ExemplaireDto();

        exemplaireDto.setId( exemplaire.getId() );
        exemplaireDto.setDisponible( exemplaire.isDisponible() );
        exemplaireDto.setOuvrage( exemplaire.getOuvrage() );
        exemplaireDto.setBibliotheque( exemplaire.getBibliotheque() );
        exemplaireDto.setUser( exemplaire.getUser() );
        exemplaireDto.setEmprunt( exemplaire.getEmprunt() );

        return exemplaireDto;
    }

    @Override
    public Exemplaire from(ExemplaireDto exemplaireDto) {
        if ( exemplaireDto == null ) {
            return null;
        }

        Exemplaire exemplaire = new Exemplaire();

        exemplaire.setId( exemplaireDto.getId() );
        exemplaire.setOuvrage( exemplaireDto.getOuvrage() );
        exemplaire.setBibliotheque( exemplaireDto.getBibliotheque() );
        exemplaire.setEmprunt( exemplaireDto.getEmprunt() );
        exemplaire.setDisponible( exemplaireDto.isDisponible() );
        exemplaire.setUser( exemplaireDto.getUser() );

        return exemplaire;
    }
}
