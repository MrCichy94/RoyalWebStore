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
import pl.cichy.RoyalWebStore.logic.CopyService;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
class CopyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    @DisplayName("should get all copies")
    void readAllCopies_shouldGetAllCopies() throws Exception {
        //given
        Copy first = new Copy(
                1,
                "BEA111-006-11",
                new BigDecimal("119.99"),
                new BigDecimal("0.12"));
        Copy second = new Copy(
                2,
                "BEA222-006-11",
                new BigDecimal("129.99"),
                new BigDecimal("0.14"));
        copyRepository.save(first);
        copyRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/copies"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].merchandisingCode", Matchers.is("BEA222-006-11")));
    }

    @Test
    @Transactional
    @DisplayName("should get all copies for product with given productId")
    void readAllCopiesForProductId_shouldGetAllCopiesForWithGiveProductId() throws Exception {
        //given
        Product p1 = new Product(
                5,
                "TESTOWY",
                "Nike",
                "Obuwie",
                new BigDecimal("19.99"),
                new BigDecimal("0.20"));
        productRepository.save(p1);
        Copy first = new Copy(
                1,
                "BEA111-006-11",
                new BigDecimal("119.99"),
                new BigDecimal("0.12"));
        first.setProductId(p1.getProductId());
        Copy second = new Copy(
                2,
                "BEA222-006-11",
                new BigDecimal("129.99"),
                new BigDecimal("0.14"));
        second.setProductId(p1.getProductId());
        copyRepository.save(first);
        copyRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/" + p1.getProductId() + "/copies"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].merchandisingCode", Matchers.is("BEA222-006-11")));
    }

    @Test
    @Transactional
    @DisplayName("should post copy")
    void createNewCopyOfProduct_shouldPostACopyOfProductWithGivenId() throws Exception {
        //given
        Product p2 = new Product(
                1,
                "TESTOWY",
                "Nike",
                "Obuwie",
                new BigDecimal("19.99"),
                new BigDecimal("0.20"));

        Copy copy = new Copy();
        copy.setMerchandisingCode("BEA111-006-11");
        copy.setBuyGrossPrice(new BigDecimal("119.99"));
        copy.setBuyVatPercentage(new BigDecimal("0.12"));
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/products/" + p2.getProductId())
                .content(asJsonString(copy))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

    }

    @Test
    @Transactional
    @DisplayName("should delete copy")
    void deleteCopy_shouldDeleteACopyWithGivenId() throws Exception {
        //given
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/copies/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
