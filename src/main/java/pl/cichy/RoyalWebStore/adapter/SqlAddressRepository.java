package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Address;
import pl.cichy.RoyalWebStore.model.repository.AddressRepository;

import java.util.Optional;

@Repository
public interface SqlAddressRepository extends AddressRepository, JpaRepository<Address, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from ADDRESSES where CITY=:city")
    Optional<Address> findByCity(@Param("city") String city);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from ADDRESSES where STREET_NAME=:streetName")
    Optional<Address> findByStreetName(@Param("streetName") String streetName);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from ADDRESSES where DOOR_NUMBER=:doorNumber")
    Optional<Address> findByDoorNumber(@Param("doorNumber") String doorNumber);
}
