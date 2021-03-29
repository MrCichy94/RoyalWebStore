package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;

import java.util.Set;

@Repository
public interface SqlCopyRepository extends CopyRepository, JpaRepository<Copy, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from COPIES where PRODUCT_ID=:id")
    Set<Copy> getCopiesByProductId(@Param("id") Integer id);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from COPIES where COPY_ID=:id")
    Copy getById(@Param("id") Integer id);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from COPIES")
    Set<Copy> findAllUnique();

    @Override
    @Query(nativeQuery = true, value = "SELECT * from COPIES where MERCHANDISING_CODE=:merchandising")
    Copy getByMerchandisingCode(@Param("merchandising") String merchandising);

}
