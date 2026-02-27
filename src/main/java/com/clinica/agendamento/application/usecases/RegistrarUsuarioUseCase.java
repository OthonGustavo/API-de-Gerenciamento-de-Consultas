package com.clinica.agendamento.application.usecases;

import com.clinica.agendamento.domain.models.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

interface UsuarioRepositoryMock {
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
    Usuario save(Usuario u);
}

@Service
public class RegistrarUsuarioUseCase {

    private final UsuarioRepositoryMock usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrarUsuarioUseCase(UsuarioRepositoryMock usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registrarPacienteLocal(String nome, String email, String senhaPlana, String ipUsuario) {
        if (usuarioRepository.existsByEmail(email)) throw new RuntimeException("E-mail ja cadastrado.");

        Usuario novo = new Usuario();
        novo.setNome(nome);
        novo.setEmail(email);
        novo.setSenhaHash(passwordEncoder.encode(senhaPlana));
        novo.setPerfil(Usuario.PerfilAcesso.PACIENTE);
        novo.setProvedorAuth("LOCAL");
        novo.setEmailVerificado(false);
        novo.setAceitouTermosEm(LocalDateTime.now());
        novo.setIpAceiteTermos(ipUsuario);
        novo.setStatusConta("ATIVO");

        return usuarioRepository.save(novo);
    }

    @Transactional
    public Usuario registrarOuLoginGoogle(String emailGoogle, String nomeGoogle, String ipUsuario) {
        return usuarioRepository.findByEmail(emailGoogle).orElseGet(() -> {
            Usuario novo = new Usuario();
            novo.setNome(nomeGoogle);
            novo.setEmail(emailGoogle);
            novo.setSenhaHash(null);
            novo.setPerfil(Usuario.PerfilAcesso.PACIENTE);
            novo.setProvedorAuth("GOOGLE");
            novo.setEmailVerificado(true);
            novo.setAceitouTermosEm(LocalDateTime.now());
            novo.setIpAceiteTermos(ipUsuario);
            novo.setStatusConta("ATIVO");
            return usuarioRepository.save(novo);
        });
    }

    @Transactional
    public Usuario solicitarContaProfissional(String nome, String email, String senhaPlana, String registro, String ip) {
        if (usuarioRepository.existsByEmail(email)) throw new RuntimeException("E-mail ja cadastrado.");

        Usuario prof = new Usuario();
        prof.setNome(nome);
        prof.setEmail(email);
        prof.setSenhaHash(passwordEncoder.encode(senhaPlana));
        prof.setPerfil(Usuario.PerfilAcesso.PROFISSIONAL);
        prof.setStatusConta("PENDENTE_APROVACAO"); 
        prof.setEmailVerificado(false);
        prof.setAceitouTermosEm(LocalDateTime.now());
        prof.setIpAceiteTermos(ip);

        return usuarioRepository.save(prof);
    }
}
