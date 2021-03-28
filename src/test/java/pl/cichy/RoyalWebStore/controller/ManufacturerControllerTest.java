package pl.cichy.RoyalWebStore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.cichy.RoyalWebStore.model.Manufacturer;
import pl.cichy.RoyalWebStore.model.repository.ManufacturerRepository;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class ManufacturerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    @Transactional
    @DisplayName("should get all manufacturers")
    void readAllManufacturers_shouldGetAllManufacturers() throws Exception {
        //given
        Manufacturer first = new Manufacturer("Adidas");
        Manufacturer second = new Manufacturer("Nike");
        manufacturerRepository.save(first);
        manufacturerRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/manufacturers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturerName", Matchers.is("Adidas")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manufacturerName", Matchers.is("Nike")));
    }

    @Test
    @Transactional
    @DisplayName("should set a manufacturer for product")
    void setNewManufacturerForProduct_shouldSetManufacturerForProductWithGivenId() throws Exception {
        //given
        Manufacturer manufacturer = new Manufacturer("Adidas");
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/products/1/manufacturer/add")
                .content(asJsonString(manufacturer))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
