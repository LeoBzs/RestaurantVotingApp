package restaurant.voting.system.service;

import restaurant.voting.system.modelo.Usuario;
import restaurant.voting.system.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;


@Service
public class UsuarioServiceImpl implements UsuarioService {


    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> findById(Long Id) {
        return usuarioRepository.findById(Id);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) throws DataAccessException {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Iterable<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> vote(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setVote(0);
                    return usuarioRepository.save(usuario);
                });
    }

    @Override
    public Stream<Usuario> whoCanVote(){
        return usuarioRepository.findAll()
                .stream()
                .filter(usuario ->
                    usuario.getVote().equals(1)
                ).map(usuarioRepository::save);
    }
}
