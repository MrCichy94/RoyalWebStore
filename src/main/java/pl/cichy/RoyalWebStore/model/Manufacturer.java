package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table( name = "manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int manufacturerId;

    @Size(min=1, max=25)
    String manufacturerName;

    public Manufacturer() {
    }

    public Manufacturer(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}
