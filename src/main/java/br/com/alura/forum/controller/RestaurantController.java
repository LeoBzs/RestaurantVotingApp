package br.com.alura.forum.controller;

import br.com.alura.forum.modelo.Restaurant;
import br.com.alura.forum.service.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantServiceImpl restaurantService;

    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
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
    public ResponseEntity<Optional<Restaurant>> voteForRestaurant(@PathVariable @Valid Long id, @RequestParam String email) {
        return new ResponseEntity<>(restaurantService.voteForRestaurant(id, email), HttpStatus.OK);
    }

    @GetMapping("/winner")
    public ResponseEntity<Stream<Restaurant>> winner() {
        return new ResponseEntity<>(restaurantService.mostVoted(), HttpStatus.OK);
    }

    @GetMapping("/toppicks")
    public ResponseEntity<Stream<Restaurant>> topPicks() {
        return new ResponseEntity<>(restaurantService.topPicks(), HttpStatus.OK);
    }

    //  not working at all
    @GetMapping("/voteabletomorrow/{id}")
    public ResponseEntity<Optional<Restaurant>> canBeVoted(@PathVariable @Valid Long id){
        return new ResponseEntity<>(restaurantService.canBeVotedNextDay(id), HttpStatus.OK);
    }

}