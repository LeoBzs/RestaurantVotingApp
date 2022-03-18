package br.com.alura.forum.scheduled;

import br.com.alura.forum.repository.RestaurantRepository;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static br.com.alura.forum.modelo.StatusVote.VALID;
import static br.com.alura.forum.modelo.StatusVote.WINNER;
import static br.com.alura.forum.modelo.Usuario.StatusEnumVoter.ALREADY_VOTED;
import static br.com.alura.forum.modelo.Usuario.StatusEnumVoter.CAN_VOTE;

@Component
@EnableScheduling
public class SchedulerService {
    private final UsuarioRepository usuarioRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public SchedulerService(UsuarioRepository usuarioRepository, RestaurantRepository restaurantRepository) {
        this.usuarioRepository = usuarioRepository;
        this.restaurantRepository = restaurantRepository;
    }

    //para teste, roda cada 4 minutos: @Scheduled(fixedRate = 240000)
    //concede 1 voto diário para todos que já votaram no dia anterior, roda cada meio-dia da semana:
    @Scheduled(cron = "0 0 12 * * ?")
    public void voteManager(){
        usuarioRepository.findAll()
                .stream()
                .filter(usuario ->
                        usuario.getStatus().equals(ALREADY_VOTED)
                )
                .map(usuario -> {
                    usuario.setVote(1);
                    usuario.setStatus(CAN_VOTE);
                    return usuarioRepository.save(usuario);
                })
                .collect(Collectors.toList());
    }

    //re-valida o restaurante para ser votado, toda segunda, resetando o status do restaurante:
    @Scheduled(cron = "0 0 0 * * MON")
    public void restaurantValidityManager(){
        restaurantRepository.findAll()
                .stream()
                .filter(restaurant ->
                        restaurant.getStatus().equals(WINNER)
                )
                .map(restaurant -> {
                   restaurant.setStatus(VALID);
                    return restaurantRepository.save(restaurant);
                })
                .collect(Collectors.toList());
    }

}
