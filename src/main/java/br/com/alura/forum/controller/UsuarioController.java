package br.com.alura.forum.controller;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    @Autowired
    public UsuarioController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody @Valid Usuario usuario, BindingResult bindingResult) {

        Usuario saveUsuario = usuarioService.saveUsuario(usuario);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(usuario, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(saveUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable @Valid Long id) {

        Optional<Usuario> usuario;
        usuario = usuarioService.findById(id);

        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/whocanvote")
    public ResponseEntity<Stream<Usuario>> whoCanVote() {
        return new ResponseEntity<>(usuarioService.whoCanVote(), HttpStatus.OK);
    }

}
