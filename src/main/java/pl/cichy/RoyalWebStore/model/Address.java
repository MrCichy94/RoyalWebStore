package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int addressId;

    @Size(max = 25)
    String country;

    @Size(min = 3, max = 25)
    String city;

    @Size(min = 3, max = 25)
    String voivodeship;

    @Size(max = 25)
    String county;

    @Size(min = 3, max = 25)
    String zipCode;

    @Size(min = 3, max = 25)
    String streetName;

    @Size(min = 1, max = 25)
    String doorNumber;

    @Size(max = 25)
    String localNumber;

    public Address() {
    }

    public Address(int addressId, String city, String voivodeship, String zipCode,
                   String streetName, String doorNumber) {
        this.addressId = addressId;
        this.city = city;
        this.voivodeship = voivodeship;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.doorNumber = doorNumber;
    }
}
