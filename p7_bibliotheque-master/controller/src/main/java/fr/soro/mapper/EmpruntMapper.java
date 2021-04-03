package fr.soro.mapper;

import fr.soro.dto.EmpruntDto;
import fr.soro.entities.Emprunt;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpruntMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id",target = "id")
    @Mapping(source = "dateDebut",target = "dateDebut")
    @Mapping(source = "dateEcheance",target = "dateEcheance")
    @Mapping(source = "prolongation",target = "prolongation")
    @Mapping(source = "depassement",target = "depassement")
    EmpruntDto from(Emprunt emprunt);

    Emprunt from(EmpruntDto empruntDto);
}
