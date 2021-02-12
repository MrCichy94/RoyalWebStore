package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table( name = "categories_manufacturers")
public class CategoryAndManufacturer {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int categoriesManufacturerId;

    @OneToOne
    Category category;

    @OneToOne
    Manufacturer manufacturer;

    public CategoryAndManufacturer() {
    }
}
