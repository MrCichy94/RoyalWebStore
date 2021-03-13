package pl.cichy.RoyalWebStore.model.repository;

import pl.cichy.RoyalWebStore.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String login);

    User save(User entity);

}
