package restaurant.voting.system.controller;

import org.springframework.format.annotation.DateTimeFormat;
import restaurant.voting.system.modelo.Restaurant;
import restaurant.voting.system.repository.UsuarioRepository;
import restaurant.voting.system.service.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantServiceImpl restaurantService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantService, UsuarioRepository usuarioRepository) {
        this.restaurantService = restaurantService;
        this.usuarioRepository = usuarioRepository;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody @Valid Restaurant restaurant, BindingResult bindingResult) {

        Restaurant saveRestaurant = restaurantService.saveRestaurant(restaurant);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(restaurant, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(saveRestaurant, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(restaurantService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> searchRestaurant(@PathVariable @Valid Long id) {

        Optional<Restaurant> getRestaurant;
        getRestaurant = restaurantService.findById(id);

        return getRestaurant.map(value -> new ResponseEntity<>(value, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/vote/{id}")
    public ResponseEntity<Optional<Restaurant>> voteForRestaurant(@PathVariable @Valid Long id, @RequestParam String email, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
       if(restaurantService.findById(id).isPresent() && usuarioRepository.findByEmail(email).get().getVote().equals(1)) {
           return new ResponseEntity<>(restaurantService.voteForRestaurant(id, email, date), HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/winner")
    public ResponseEntity<Stream<Restaurant>> winner() {
        if(!restaurantService.waitsUntilHalfHaveVoted()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(restaurantService.mostVoted(), HttpStatus.OK);
    }

    @GetMapping("/toppicks")
    public ResponseEntity<Stream<Restaurant>> topPicks() {
        return new ResponseEntity<>(restaurantService.topPicks(), HttpStatus.OK);
    }

}