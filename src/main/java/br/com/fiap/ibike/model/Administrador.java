package br.com.fiap.ibike.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.fiap.ibike.View;
import br.com.fiap.ibike.components.StatusAdministrador;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrador implements UserDetails{
    @Id
    @Column(length = 11)
    @JsonView(View.PatioFull.class)
    private String cpf;

    @Column(name = "nm_adm", nullable = false)
    @JsonView(View.PatioFull.class)
    private String nome;

    @Email
    @NotBlank
     @Column(unique = true)
     @JsonView(View.PatioFull.class)
    private String email;

    @NotBlank
    @Size(min = 5)
    @Column(nullable = false, length = 100)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonView(View.PatioFull.class)
    private StatusAdministrador status=StatusAdministrador.ATIVO;

    @ManyToOne
    @JoinColumn(name = "id_patio", nullable = false) // nome da coluna FK no banco
    @JsonIgnore
    private Patio patio;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(status.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }


}

