package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.User;
import pl.cichy.RoyalWebStore.model.repository.UserRepository;

@Repository
public interface SqlUserRepository extends UserRepository, JpaRepository<User, Integer> {
}
