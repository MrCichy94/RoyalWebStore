package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;

public interface SqlCopyRepository extends CopyRepository, JpaRepository<Copy, Integer> {
}
