package fr.soro.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.soro.Client.ReservationClient;
import fr.soro.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ReservationClientController {

    @Autowired
    private ReservationClient reservationClient;




    @GetMapping("/create-reservation/{ouvrageId}")
    public ModelAndView createReservations(@PathVariable Long ouvrageId, HttpSession session, ModelAndView modelAndView) throws JsonProcessingException {
        UserDto user =(UserDto) session.getAttribute("userSession");
        CreateReservationDto createReservationDto = reservationClient.createReservation(user.getId(), ouvrageId);
//        ModelAndView modelAndView = new ModelAndView("redirect:/infos-reservation/"+ouvrageId);
        WaitingListCredentialsDto infos = reservationClient.getOuvrageWaitingListCredentials(ouvrageId);
        modelAndView.addObject("infos", infos);
        modelAndView.addObject("reservation", createReservationDto);
        modelAndView.setViewName("reservation-confirmer-alerte");
        return modelAndView;
    }

    @GetMapping("/user-reservations")
    public ModelAndView getUserReservations( ModelAndView modelAndView, HttpSession session){
        UserDto user =(UserDto) session.getAttribute("userSession");
        List<UserReservationCredentialsDto> reservations = reservationClient.getAllUserReservationCredentials(user.getId());
        modelAndView.addObject("reservations", reservations);
        modelAndView.setViewName("user-reservations");
        return modelAndView;
    }

    @GetMapping("/delete/reservations/{id}")
    public ModelAndView deleteReservation(@PathVariable(value = "id") Long id, HttpSession session) {
        reservationClient.deleteReservation(id);
        UserDto user =(UserDto) session.getAttribute("userSession");
        ModelAndView modelAndView = new ModelAndView("redirect:/user-reservations/"+user.getId());
        return modelAndView;
    }


}




















