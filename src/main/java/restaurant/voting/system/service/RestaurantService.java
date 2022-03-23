package restaurant.voting.system.service;

import restaurant.voting.system.modelo.Restaurant;
import restaurant.voting.system.modelo.Usuario;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public interface RestaurantService {

    Optional<Restaurant> findById(Long id) throws DataAccessException;

    Optional<Restaurant> voteForRestaurant(Long id, String email, LocalDate date) throws DataAccessException;

    Restaurant saveRestaurant(Restaurant restaurant) throws DataAccessException;

    Optional<Restaurant> canBeVotedNextDay(Long Id);

    Iterable<Restaurant> findAll();

    List<Usuario> usersWhoVotedList();

    Stream<Restaurant> topPicks();

    Stream<Restaurant> mostVoted();

    boolean userIsAllowedToVote(String email);

    boolean restaurantIsValid(Long Id);

}
