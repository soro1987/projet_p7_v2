package fr.soro.mapper;

import fr.soro.dto.EmpruntDto;
import fr.soro.entities.Emprunt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpruntMapper {

    EmpruntDto from(Emprunt emprunt);

    Emprunt from(EmpruntDto empruntDto);
}
