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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.cichy.RoyalWebStore.logic.CopyService;
import pl.cichy.RoyalWebStore.model.Address;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    private CopyService copyService;
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
                2,
                "TESTOWY",
                new BigDecimal("19.99"),
                new BigDecimal("0.20"));
        productRepository.save(p1);
        Copy first = new Copy(
                1,
                "BEA111-006-11",
                new BigDecimal("119.99"),
                new BigDecimal("0.12"));
        first.setProductId(2);
        Copy second = new Copy(
                2,
                "BEA222-006-11",
                new BigDecimal("129.99"),
                new BigDecimal("0.14"));
        second.setProductId(2);
        copyRepository.save(first);
        copyRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/"+p1.getProductId()+"/copies"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].merchandisingCode", Matchers.is("BEA222-006-11")));
    }

    @Test
    @Transactional
    @DisplayName("should post copy")
    void addCopy() throws Exception {
        //given
        Copy copy = new Copy(
                100,
                "BEA111-006-11",
                new BigDecimal("119.99"),
                new BigDecimal("0.12"));
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/products/1")
                .content(asJsonString(copy))
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
