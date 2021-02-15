package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;

import java.util.List;

@Repository
public interface SqlCopyRepository extends CopyRepository, JpaRepository<Copy, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from COPIES where PRODUCT_ID=:id")
    List<Copy> getCopiesByProductId(@Param("id") Integer id);

}
