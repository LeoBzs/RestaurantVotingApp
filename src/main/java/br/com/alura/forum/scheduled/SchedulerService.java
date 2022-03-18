package br.com.alura.forum.scheduled;

import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static br.com.alura.forum.modelo.Usuario.StatusEnumVoter.ALREADY_VOTED;
import static br.com.alura.forum.modelo.Usuario.StatusEnumVoter.CAN_VOTE;

@Component
@EnableScheduling
public class SchedulerService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public SchedulerService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
}
