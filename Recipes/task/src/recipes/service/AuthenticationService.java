package recipes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.entity.User;
import recipes.exception.IllegalCredentialException;
import recipes.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void register(User user) {
        Optional<User> optionalUser = userRepository.findUserByEmailIgnoreCase(user.getEmail());
        if (optionalUser.isPresent()) throw new IllegalCredentialException("User with this password already exist");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmailIgnoreCase(email);
        return optionalUser.orElseThrow(() -> new IllegalArgumentException("User with this email doesn't exist"));
    }

}
