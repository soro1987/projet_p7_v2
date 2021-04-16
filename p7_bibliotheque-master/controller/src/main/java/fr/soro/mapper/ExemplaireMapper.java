package fr.soro.mapper;

import fr.soro.dto.ExemplaireDto;
import fr.soro.entities.Exemplaire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExemplaireMapper {

    ExemplaireDto from(Exemplaire exemplaire);

    Exemplaire from(ExemplaireDto exemplaireDto);

}
