package br.com.fiap.ibike.controller;

import br.com.fiap.ibike.components.StatusAdministrador;
import br.com.fiap.ibike.model.Administrador;
import br.com.fiap.ibike.model.Patio;
import br.com.fiap.ibike.model.dto.AdministradorResponse.UserResponse;
import br.com.fiap.ibike.repository.AdministradorRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdministradorRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
	public List<Administrador> index() {
		return repository.findAll();
	}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid Administrador admin) {
        admin.setStatus(StatusAdministrador.ATIVO);
       admin.setPassword(passwordEncoder.encode(admin.getPassword()));
       if (repository.existsById(admin.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Administrador com este CPF já existe.");
        }
        var userSaved=repository.save(admin);
        return new UserResponse(userSaved.getCpf(),userSaved.getEmail(),userSaved.getStatus());
    }

    @GetMapping("/me")
    public Administrador getMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador não encontrado"));
    }

    // Atualizar dados do próprio administrador
    @PutMapping("/alteracao")
    public Administrador updateMe(@RequestBody @Valid Administrador administradorAtualizado) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        Administrador administrador = repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador não encontrado"));

        // Atualize os campos permitidos
        administrador.setNome(administradorAtualizado.getNome());

        // Se quiser atualizar a senha, encode antes
        if (administradorAtualizado.getPassword() != null && !administradorAtualizado.getPassword().isBlank()) {
            administrador.setPassword(passwordEncoder.encode(administradorAtualizado.getPassword()));
        }

        return repository.save(administrador);
    }
    @Transactional
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Administrador administrador = repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador não encontrado"));
        
        Patio patio = administrador.getPatio();

        if (patio != null) {
        patio.getAdministradores().remove(administrador);
        }
        repository.delete(administrador);
        log.info("Administrador deletado: " + email);
    }


    
}
