package restaurant.voting.system.scheduled;

import restaurant.voting.system.repository.RestaurantRepository;
import restaurant.voting.system.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import restaurant.voting.system.modelo.StatusVote;
import restaurant.voting.system.modelo.Usuario;

import java.util.stream.Collectors;

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
                        usuario.getStatus().equals(Usuario.StatusEnumVoter.ALREADY_VOTED)
                )
                .map(usuario -> {
                    usuario.setVote(1);
                    usuario.setStatus(Usuario.StatusEnumVoter.CAN_VOTE);
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
                        restaurant.getStatus().equals(StatusVote.WINNER)
                )
                .map(restaurant -> {
                   restaurant.setStatus(StatusVote.VALID);
                    return restaurantRepository.save(restaurant);
                })
                .collect(Collectors.toList());
    }

}
