package fr.soro.restcontroller;

import fr.soro.Client.ReservationClient;
import fr.soro.dto.EmpruntDto;
import fr.soro.dto.UserDto;
import fr.soro.dto.UserReservationCredentialsDto;
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

    @GetMapping("/user-reservations/{id}")
    public ModelAndView getUserReservations(@PathVariable(value = "id") Long userId, ModelAndView modelAndView){
        List<UserReservationCredentialsDto> reservations = reservationClient.getAllUserReservationCredentials(userId);
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




















