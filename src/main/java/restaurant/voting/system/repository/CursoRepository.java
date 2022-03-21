package restaurant.voting.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import restaurant.voting.system.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nome);

}
