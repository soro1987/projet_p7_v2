package fr.soro.restcontroller;

import fr.soro.Client.ReservationClient;
import fr.soro.dto.EmpruntDto;
import fr.soro.dto.UserReservationCredentialsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ReservationController {

    private ReservationClient reservationClient;

    @GetMapping("/user-reservations/{id}")
    public ModelAndView getUserReservations(@PathVariable(value = "id") Long userId, ModelAndView modelAndView){
        List<UserReservationCredentialsDto> reservations = reservationClient.getAllUserReservationCredentials(userId);
        modelAndView.addObject("reservations", reservations);
        modelAndView.setViewName("user-reservations");
        return modelAndView;

    }
}
