package fr.soro.mapper;

import fr.soro.dto.OuvrageDto;
import fr.soro.entities.Ouvrage;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OuvrageMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id",target = "id")
    @Mapping(source = "auteur",target = "auteur")
    @Mapping(source = "titre",target = "titre")
    @Mapping(source = "dateParution",target = "dateParution")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "categorie",target = "categorie")
    @Mapping(source = "nbreExemplaireDispo",target = "nbreExemplaireDispo")
    @Mapping(source = "imageUrl",target = "imageUrl")
    @Mapping(source = "image",target = "image")
    OuvrageDto from(Ouvrage ouvrage);

    Ouvrage from(OuvrageDto ouvrage);
}
