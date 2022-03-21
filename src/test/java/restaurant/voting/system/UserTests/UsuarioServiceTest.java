package restaurant.voting.system.UserTests;
/*
import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import br.com.alura.forum.service.UsuarioServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static br.com.alura.forum.modelo.Usuario.StatusEnumVoter.CAN_VOTE;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Usuario Service Teste")
class UsuarioServiceTest {
    @Autowired
    private UsuarioServiceImpl usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    private final ArrayList<Usuario> usuarioArrayList = new ArrayList<>();
    private Usuario usuario;

    @BeforeEach
    void setup() {

        MockitoAnnotations.initMocks(this);
        usuarioService = new UsuarioServiceImpl(usuarioRepository);

        usuario = Usuario.builder().nome("Voter").email("voter@email.com").senha("123456").status(CAN_VOTE).vote(1).build();
    }

    @DisplayName("Testing the save method from UsuarioService")
    @Test
    void saveUsuario() {

        when(usuarioService.saveUsuario(usuario)).thenReturn(usuario);
        Assertions.assertEquals(usuario, usuarioService.saveUsuario(usuario));
    }

    @Test
    void findAllUsuarios() {

        when(usuarioService.findAll()).thenReturn(usuarioArrayList);
        Assertions.assertEquals(usuario, usuarioService.findAll());
    }
}

 */