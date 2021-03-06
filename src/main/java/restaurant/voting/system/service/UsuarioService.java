package restaurant.voting.system.service;

import restaurant.voting.system.modelo.Usuario;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public interface UsuarioService {

    Optional<Usuario> findById(Long Id);

    Usuario saveUsuario(Usuario usuario) throws DataAccessException;

    Iterable<Usuario> findAll();

    Optional<Usuario> vote(Long Id);

    Stream<Usuario> whoCanVote();

}