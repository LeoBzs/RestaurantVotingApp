package restaurant.voting.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import restaurant.voting.system.modelo.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);

}
