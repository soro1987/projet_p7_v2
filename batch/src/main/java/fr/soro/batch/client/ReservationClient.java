package fr.soro.batch.client;

import fr.soro.batch.modele.MailForExpiredReservationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReservationClient {

    private RestTemplate restTemplate;

    @Value("${app.serveur.url}")
    private String appUrl;

    public ReservationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MailForExpiredReservationDto> getAllMailForExpiredReservation(){
        ResponseEntity<MailForExpiredReservationDto[]> response =restTemplate.postForEntity(
                appUrl+"reservations/expired",null,MailForExpiredReservationDto[].class);
        return Optional.ofNullable(response.getBody()).map(Arrays::asList).orElse(Collections.emptyList());
    }

}
