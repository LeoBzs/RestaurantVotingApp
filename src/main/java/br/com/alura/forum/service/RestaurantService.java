package br.com.alura.forum.service;

import br.com.alura.forum.modelo.Restaurant;
import br.com.alura.forum.modelo.Usuario;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public interface RestaurantService {

    Optional<Restaurant> findById(Long id) throws DataAccessException;

    Optional<Restaurant> voteForRestaurant(Long id, String email) throws DataAccessException;

    Restaurant saveRestaurant(Restaurant restaurant) throws DataAccessException;

    Optional<Restaurant> canBeVotedNextDay(Long Id);

    boolean userIsAllowedToVote(String email);

    Iterable<Restaurant> findAll();

    Stream<Restaurant> topPicks();

    List<Usuario> usersWhoVotedList();

    Stream<Restaurant> mostVoted();

}