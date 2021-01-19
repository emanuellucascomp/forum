package br.com.wb.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.wb.forum.controller.dto.DetalheTopicoDTO;
import br.com.wb.forum.controller.dto.TopicoDTO;
import br.com.wb.forum.controller.form.AtualizacaoTopicoForm;
import br.com.wb.forum.controller.form.TopicoForm;
import br.com.wb.forum.model.Topico;
import br.com.wb.forum.repository.CursoRepository;
import br.com.wb.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso, @RequestParam int pagina, @RequestParam int qtd){
		Pageable paginacao = PageRequest.of(pagina, qtd);
		
		if(nomeCurso != null) {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDTO.converter(topicos);
		}
		Page<Topico> topicos = topicoRepository.findAll(paginacao);
		return TopicoDTO.converter(topicos);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
		Topico topico = topicoForm.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalheTopicoDTO> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			return ResponseEntity.ok(new DetalheTopicoDTO(topico.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<DetalheTopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm topicoForm) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			Topico topicoAtualizado = topicoForm.atualizar(topico.get().getId(), topicoRepository);
			return ResponseEntity.ok(new DetalheTopicoDTO(topicoAtualizado));			
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			topicoRepository.deleteById(topico.get().getId());
			return ResponseEntity.ok().build();			
		}
		return ResponseEntity.notFound().build();
	}
}









