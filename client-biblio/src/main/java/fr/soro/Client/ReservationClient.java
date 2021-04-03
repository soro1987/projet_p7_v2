package fr.soro.Client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.soro.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReservationClient {

    @Value("${app.serveur.url}")
    private String appUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplate securedRestTemplate;

    public ReservationClient() throws JsonProcessingException {
    }

    public CreateReservationDto createReservation(String ouvrageId) throws JsonProcessingException {
        ResponseEntity<CreateReservationDto> registrationResponse = securedRestTemplate.exchange(appUrl + "/reservations",
                HttpMethod.POST, this.registrationEntityBuilder(), CreateReservationDto.class);
        return registrationResponse.getBody();
    }

    public void deleteReservation(String reservationId) {
        securedRestTemplate.delete(appUrl+ "/reservations/" + reservationId);
    }

    public WaitingListCredentialsDto getOuvrageWaitingListCredentials(String ouvrageId){
        ResponseEntity<WaitingListCredentialsDto> response =securedRestTemplate.getForEntity(appUrl+"/v1/reservations/waitingList/"+ouvrageId, WaitingListCredentialsDto.class);
        return response.getBody();
    }

    public List<UserReservationCredentialsDto> getAllUserReservationCredentials(Long userId){
        ResponseEntity<UserReservationCredentialsDto[]> response =securedRestTemplate.getForEntity(appUrl+"/v1/reservation/userCredentials/"+userId, UserReservationCredentialsDto[].class);
        return Optional.ofNullable(response.getBody()).map(Arrays::asList).orElse(Collections.emptyList());
    }

    public UserReservationCredentialsDto getUserReservationCredentials(String userId){
        ResponseEntity<UserReservationCredentialsDto> response =securedRestTemplate.getForEntity(appUrl+"/v1/reservation/userCredentials/"+userId, UserReservationCredentialsDto.class);
        return response.getBody();
    }

    public HttpEntity<String>  registrationEntityBuilder() throws JsonProcessingException {
        HttpHeaders reservationHeaders = getHeaders();
        String reservationBody = this.getBody(new CreateReservationDto());
        return new HttpEntity<String>(reservationBody,reservationHeaders);
    }

    private String getBody(final CreateReservationDto CreateReservationDto) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(CreateReservationDto);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
