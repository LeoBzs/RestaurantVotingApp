package restaurant.voting.system.UserTests;
/*
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.service.UsuarioServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Usuario Controller Teste")
public class UsuarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UsuarioServiceImpl usuarioService;

    Usuario usuario = Usuario.builder().email("voter@email.com").senha("123456").build();

    @Test
    @DisplayName("Teste Post do Controller do Usuario")
    public void CriarCartaDoJogo() throws Exception {

        when(usuarioService.saveUsuario(any(Usuario.class))).thenReturn(usuario);

        ObjectMapper mapper = new ObjectMapper();
        String usuarioJSON = mapper.writeValueAsString(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth").content(usuarioJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.content().json(usuarioJSON));

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario").content(usuarioJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.content().json(usuarioJSON));
    }

    @Test
    @DisplayName("Teste do GET do Controller Usuario")
    public void deveRetornarSucesso_QuandoBuscarUsuarios() throws Exception {

        given(usuarioService.findAll().equals(usuario));

        ObjectMapper mapper = new ObjectMapper();
        String usuarioJSON = mapper.writeValueAsString(usuario);

        mockMvc.perform(get("/usuario/all" + usuario.getId()).content(usuarioJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().json(usuarioJSON));

    }

}

 */