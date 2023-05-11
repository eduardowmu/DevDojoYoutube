package br.edu.devdojo2.app.service;

import br.edu.devdojo2.app.model.AuthUser;
import br.edu.devdojo2.app.repository.AuthUserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {
    private final AuthUserRepository authUserRepository;

    @Autowired
    public AuthUserService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) /*throws UsernameNotFoundException*/ {
        return Optional.ofNullable(this.authUserRepository.findByUserName(username))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
