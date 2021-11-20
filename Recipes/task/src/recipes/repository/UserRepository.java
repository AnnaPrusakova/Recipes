package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmailIgnoreCase(@NotBlank @Email String email);
}
