package fr.soro.mapper;

import fr.soro.dto.OuvrageDto;
import fr.soro.entities.Ouvrage;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-09T20:24:12+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_282 (Private Build)"
)
@Component
public class OuvrageMapperImpl implements OuvrageMapper {

    @Override
    public OuvrageDto from(Ouvrage ouvrage) {
        if ( ouvrage == null ) {
            return null;
        }

        OuvrageDto ouvrageDto = new OuvrageDto();

        ouvrageDto.setId( ouvrage.getId() );
        ouvrageDto.setTitre( ouvrage.getTitre() );
        ouvrageDto.setAuteur( ouvrage.getAuteur() );
        ouvrageDto.setDateParution( ouvrage.getDateParution() );
        ouvrageDto.setDescription( ouvrage.getDescription() );
        ouvrageDto.setCategorie( ouvrage.getCategorie() );
        ouvrageDto.setNbreExemplaireDispo( ouvrage.getNbreExemplaireDispo() );
        ouvrageDto.setImageUrl( ouvrage.getImageUrl() );

        return ouvrageDto;
    }

    @Override
    public Ouvrage from(OuvrageDto ouvrage) {
        if ( ouvrage == null ) {
            return null;
        }

        Ouvrage ouvrage1 = new Ouvrage();

        ouvrage1.setId( ouvrage.getId() );
        ouvrage1.setTitre( ouvrage.getTitre() );
        ouvrage1.setAuteur( ouvrage.getAuteur() );
        ouvrage1.setDateParution( ouvrage.getDateParution() );
        ouvrage1.setDescription( ouvrage.getDescription() );
        ouvrage1.setCategorie( ouvrage.getCategorie() );
        ouvrage1.setNbreExemplaireDispo( ouvrage.getNbreExemplaireDispo() );
        ouvrage1.setImageUrl( ouvrage.getImageUrl() );

        return ouvrage1;
    }
}
