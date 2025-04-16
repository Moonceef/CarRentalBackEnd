package ma.est.carrentalnm.Controller;
import ma.est.carrentalnm.dtos.RegistreResponse;
import ma.est.carrentalnm.entities.User;
import ma.est.carrentalnm.repositories.UserRepository;
import ma.est.carrentalnm.service.Jwtservice;
import ma.est.carrentalnm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;


@RestController
public class UserController {
    private final UserService userService;




    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }




    @GetMapping("/user")
    public
    Map<String , Object> user(Principal principal) {
        String email = principal.getName();
        Optional<User> userOptional = userService.getUserByEmail(email);
        return Map.of("user",principal,"userDetail",userOptional);
    }


    @PostMapping("/register")
    public ResponseEntity<RegistreResponse> register(@RequestBody User user) {
        RegistreResponse response = userService.register(user);
        return ResponseEntity.ok(response); // Retourne une réponse HTTP 200 avec l'objet JSON
    }



    @PostMapping("/login")
    public String login(@RequestBody User user){
        System.out.println(user);
        return userService.validate(user);
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

}
