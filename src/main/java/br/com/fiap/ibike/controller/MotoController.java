package br.com.fiap.ibike.controller;

import br.com.fiap.ibike.components.StatusMoto;
import br.com.fiap.ibike.model.Moto;
import br.com.fiap.ibike.repository.MotoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;;

@RestController
@RequestMapping("/motos")
public class MotoController {

    public final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MotoRepository repository;

    //Get todas as motos
    @GetMapping
    @Cacheable("motos")
    @Operation(tags = "Moto", summary = "Listar motos registradas", description = "Devolve lista de motos")
    public List<Moto>index(){
        return repository.findAll();
    }

    //Post cadastrar novas motos
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(responses = @ApiResponse(responseCode = "400", description = "Validação falhou"))
    @CacheEvict(value = "motos", allEntries = true)
    public ResponseEntity<Moto> create (@RequestBody @Valid Moto moto){
        log.info("Cadastrando moto" + moto.getPlaca());
        repository.save(moto);
        return ResponseEntity.status(201).body(moto);
    
    }

    // Buscas com paginação
    @GetMapping("/status")
    public Page<Moto> getByStatus(@RequestParam String status, Pageable pageable) {
        StatusMoto statusEnum = StatusMoto.valueOf(status.toUpperCase());
        return repository.findByStatus(statusEnum, pageable);
    }

    @GetMapping("/patio")
    public Page<Moto> getByPatio(@RequestParam String nome, Pageable pageable) {
        return repository.findByPatio_Nome(nome, pageable);
    }

    // Busca por status e pátio combinados
    @GetMapping("/filtro")
    public Page<Moto> getByStatusAndPatio(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String nome,
            Pageable pageable) {
        StatusMoto statusEnum = StatusMoto.valueOf(status.toUpperCase());
        return repository.findByStatusAndPatio(statusEnum, nome, pageable);
    }


}
