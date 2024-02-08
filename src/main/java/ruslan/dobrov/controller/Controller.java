package ruslan.dobrov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.services.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class Controller {

    private final RegistrationService registrationService;
    private final PersonService personService;

    @GetMapping("/after-login")
    public String home(@AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("name");
        String email = principal.getAttribute("email");

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

//        log.info(personDetails.getPerson().toString());


        Optional<Person> person = personService.findByUsername(username);
//        log.info(person.get().toString());
        if (person.isPresent() && person.get().getRoles().contains("ADMIN")) {
            return "redirect:/admin";
        }
        if (person.isPresent() && person.get().getRoles().contains("USER")) {
            return "redirect:/user";
        }
        registrationService.register(new Person(username, email,"123"));
//        log.info("after /after-login endpoint");
        return "redirect:/user";

//        if (personDetails.getAuthorities().contains("ROLE_ADMIN")) {
//            return "redirect:/admin";
//        } else if (personDetails.getAuthorities().contains("ROLE_USER")) {
//            return "redirect:/user";
//        } else {
//            registrationService.register(personDetails.getPerson());
//            return "redirect:/user";
//        }
    }
}
