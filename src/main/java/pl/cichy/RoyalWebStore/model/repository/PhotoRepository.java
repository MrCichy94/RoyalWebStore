package pl.cichy.RoyalWebStore.model.repository;

import pl.cichy.RoyalWebStore.model.Photo;

import java.util.List;

public interface PhotoRepository {

    List<Photo> findAll();

    Photo save(Photo entity);

}
