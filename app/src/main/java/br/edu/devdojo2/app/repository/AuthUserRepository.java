package br.edu.devdojo2.app.repository;

import br.edu.devdojo2.app.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findByUserName(String userName);
}
