package br.com.fiap.ibike.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.ibike.View;
import br.com.fiap.ibike.model.Monitoracao;
import br.com.fiap.ibike.repository.MonitoracaoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/monitoracao")
public class MonitoracaoController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MonitoracaoRepository repository;

    @GetMapping
	public List<Monitoracao> index() {
		return repository.findAll();
	}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Monitoracao> create(@RequestBody @Valid Monitoracao monitoracao) {
		log.info("Cadastrando categoria " + monitoracao.getId());
		repository.save(monitoracao);
		return ResponseEntity.status(201).body(monitoracao);
	}
    @GetMapping("{id}")
	public Monitoracao get(@PathVariable Long id) {
		log.info("buscando categoria " + id);
		return getMonitoracao(id);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void destroy(@PathVariable Long id) {
		log.info("Apagando categoria " + id);
		repository.delete(getMonitoracao(id));
	}

	@PutMapping("{id}")
	public Monitoracao update(@PathVariable Long id, @RequestBody Monitoracao category) {
		log.info("Atualizando categoria " + id + " para " + category);

		category.setId(id);
		return repository.save(category);
	}

	private Monitoracao getMonitoracao(Long id) {
		return repository
				.findById(id)
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
