package com.clinica.agendamento.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "senha_hash")
    private String senhaHash;

    @Column(unique = true, length = 14)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilAcesso perfil;

    @Column(name = "email_verificado", nullable = false)
    private boolean emailVerificado = false;
    
    @Column(name = "provedor_auth", nullable = false)
    private String provedorAuth = "LOCAL";
    
    @Column(name = "status_conta", nullable = false)
    private String statusConta = "ATIVO";
    
    @Column(name = "aceitou_termos_em")
    private LocalDateTime aceitouTermosEm;
    
    @Column(name = "ip_aceite_termos", length = 45)
    private String ipAceiteTermos;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }

    public enum PerfilAcesso {
        PACIENTE, PROFISSIONAL, SECRETARIA, ADMIN
    }
}
