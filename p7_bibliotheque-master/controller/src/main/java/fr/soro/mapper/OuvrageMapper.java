package fr.soro.mapper;

import fr.soro.dto.OuvrageDto;
import fr.soro.entities.Ouvrage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OuvrageMapper {

    OuvrageDto from(Ouvrage ouvrage);

    Ouvrage from(OuvrageDto ouvrage);
}
