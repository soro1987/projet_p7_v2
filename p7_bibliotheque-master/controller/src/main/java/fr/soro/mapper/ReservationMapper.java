package fr.soro.mapper;

import fr.soro.dto.ReservationDto;
import fr.soro.entities.Reservation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "ouvrage.titre",target = "ouvrageName")
    @Mapping(source = "user.name",target = "userName")
    @Mapping(source = "dateReservation",target = "dateReservation")
    @Mapping(source = "id",target = "id")
    ReservationDto from(Reservation reservation);

    Reservation from(ReservationDto reservationDto);
}
