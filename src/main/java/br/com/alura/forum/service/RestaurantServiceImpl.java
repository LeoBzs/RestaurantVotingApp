package br.com.alura.forum.service;

import br.com.alura.forum.modelo.Restaurant;
import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.RestaurantRepository;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.alura.forum.modelo.StatusVote.VALID;
import static br.com.alura.forum.modelo.StatusVote.WINNER;
import static br.com.alura.forum.modelo.Usuario.StatusEnumVoter.ALREADY_VOTED;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, UsuarioRepository usuarioRepository) {
        this.restaurantRepository = restaurantRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void userVoted(String email) {
        usuarioRepository.findByEmail(email)
                .map(usuario -> {
                    usuario.setVote(0);
                    usuario.setStatus(ALREADY_VOTED);
                    return usuarioRepository.save(usuario);
                });
    }

    public int findHighestVote() {
        List<Integer> votes = restaurantRepository
                .findAll()
                .stream()
                .map(Restaurant::getVotes)
                .collect(Collectors.toList());
                return Collections.max(votes);
    }

    @Override
    public Stream<Restaurant> topPicks(){
        int totalVotes = this.usersWhoVotedList().size();
        int average = totalVotes/2;
        return restaurantRepository.findAll()
                .stream()
                .filter(restaurant -> restaurant.getVotes()>average)
                .map(restaurantRepository::save);
    }

    @Override
    public List<Usuario> usersWhoVotedList() {
        return  usuarioRepository.findAll()
                .stream()
                .filter(usuario ->
                        usuario.getStatus().equals(ALREADY_VOTED)
                ).map(usuarioRepository::save)
                .collect(Collectors.toList());
    }


    @Override
    public boolean userIsAllowedToVote(String email) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            boolean hasVoted = usuarioRepository.findByEmail(email).get().getVote().equals(0);
            return !hasVoted;
        }
        return false;
    }

    @Override
    public boolean restaurantIsValid(Long Id){
        if (restaurantRepository.findById(Id).isPresent()) {
            boolean hasWon = restaurantRepository.findById(Id).get().getStatus().equals(WINNER);
            return !hasWon;
        }
        return false;
    }

    @Override
    public Optional<Restaurant> findById(Long Id) throws DataAccessException {
        return restaurantRepository.findById(Id);
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) throws DataAccessException {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Optional<Restaurant> canBeVotedNextDay(Long Id) {
        return restaurantRepository.findById(Id)
                .filter(restaurant -> restaurant.getStatus().equals(WINNER))
                .map(restaurant -> {
                    restaurant.setStatus(VALID);
                    return restaurantRepository.save(restaurant);
                });
    }

    @Override
    public Iterable<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Optional<Restaurant> voteForRestaurant(Long Id, String email) {
        return restaurantRepository.findById(Id)
                .filter(restaurant -> this.userIsAllowedToVote(email))
                .filter(restaurant -> this.restaurantIsValid(Id))
                .map(restaurant -> {
                    restaurant.setVotes(restaurant.addVote(1));
                    this.userVoted(email);
                    return restaurantRepository.save(restaurant);
                });
    }

    @Override
    public Stream<Restaurant> mostVoted(){
        return restaurantRepository.findAll()
                .stream()
                .filter(restaurant -> restaurant.getVotes().equals(this.findHighestVote()))
                .map(restaurant -> {
                        restaurant.setStatus(WINNER);
                        return restaurantRepository.save(restaurant);
        });
    }
}

