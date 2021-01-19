package br.com.wb.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wb.forum.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	Curso findByNome(String nomeCurso);

}
