package pl.mikel.insurance.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mikel.insurance.dao.InsuranceDao;
import pl.mikel.mail.service.MailService;
import pl.mikel.security.model.User;
import pl.mikel.security.repository.UserRepository;
import pl.mikel.security.service.UserService;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {

    private UserService userService;
    private UserRepository userRepository;
    private MailService mailService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/")
    public String home(Model model){

      boolean isPresent =  SecurityContextHolder.getContext().getAuthentication() != null &&
                           SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                           !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        if(isPresent){
            User user = userRepository.findByEmail(mailService.getEmailAdress());
            model.addAttribute("user", user );
            return "homeuser";
        } else
        return "home";
    }

    @GetMapping("/logmeout")
    public String logoff(@ModelAttribute InsuranceDao insuranceDao){

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

        return "redirect:/loginform";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult bindResult) {

        Optional<User> findEmail = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if(findEmail.isPresent()){
            ObjectError objectError = new ObjectError(user.getEmail(), "zly mail");
            bindResult.addError(objectError);
            user.setEmail("");

        }
        if(bindResult.hasErrors()) {
            return "registerForm";
        }
        else {

            user.setFirstName(user.getFirstName().substring(0,1).toUpperCase() + user.getFirstName().substring(1).toLowerCase());
            user.setLastName(user.getLastName().substring(0,1).toUpperCase() + user.getLastName().substring(1).toLowerCase());

            userService.addWithDefaultRole(user);

            return "redirect:/loginform";
        }


    }
    @GetMapping("/loginform")
    public String loginForm() {
        return "login_form";
    }

    @GetMapping("/create_pdf.pdf")
    public String getPdfFile(){
        return "homeuser";
    }

    @RequestMapping(value = "/login_failed")
    public String loginFailture(){
        return "login_failed";
    }

}
