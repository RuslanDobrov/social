package ruslan.dobrov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.models.Role;
import ruslan.dobrov.services.*;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class Controller {

    private final PersonService personService;

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        Optional<Person> person = personService.findByEmail(email);
        if (person.isPresent()) {
            if (person.get().getRoles().contains(Role.ADMIN)) {
                log.info("User " + person.get().getUsername() + " with role: " + person.get().getRoles() + " was login");
                return "redirect:/admin";
            } else if (person.get().getRoles().contains(Role.USER)) {
                log.info("User " + person.get().getUsername() + " with role: " + person.get().getRoles() + " was login");
                return "redirect:/user";
            }
        }
        return "redirect:/user";
    }
}
