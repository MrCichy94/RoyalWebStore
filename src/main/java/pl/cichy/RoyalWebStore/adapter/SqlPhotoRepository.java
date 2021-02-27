package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Photo;
import pl.cichy.RoyalWebStore.model.repository.PhotoRepository;

@Repository
public interface SqlPhotoRepository extends PhotoRepository, JpaRepository<Photo, Integer> {
}
