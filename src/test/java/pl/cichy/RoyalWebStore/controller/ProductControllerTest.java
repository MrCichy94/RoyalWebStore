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
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    @DisplayName("should get all products")
    void readAllProduct_shouldGetAllProducts() throws Exception {
        //given
        Product first = new Product(
                1,
                "Revolution 5",
                "Nike",
                "Obuwie",
                new BigDecimal("119.99"),
                new BigDecimal("0.12"));
        Product second = new Product(
                2,
                "Galaxy 5",
                "Nike",
                "Obuwie",
                new BigDecimal("129.99"),
                new BigDecimal("0.14"));
        productRepository.save(first);
        productRepository.save(second);
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName", Matchers.is("Revolution 5")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName", Matchers.is("Galaxy 5")));
    }

    @Test
    @Transactional
    @DisplayName("should get single product")
    void readProductById_shouldGetSingleProductWithGivenId() throws Exception {
        //given
        Product first = new Product(
                1,
                "Revolution 6",
                "Nike",
                "Obuwie",
                new BigDecimal("119.99"),
                new BigDecimal("0.12"));
        productRepository.save(first);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + first.getProductId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        Product product = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isEqualTo(first.getProductId());
        assertThat(product.getProductName()).isEqualTo(first.getProductName());
    }

    @Test
    @Transactional
    @DisplayName("should post product")
    void createNewProduct_shouldPostNewProduct() throws Exception {
        //given
        Product product = new Product(
                1,
                "Revolution 6",
                "Nike",
                "Obuwie",
                new BigDecimal("119.99"),
                new BigDecimal("0.12"));
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/products/newproduct/add")
                .content(asJsonString(product))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @Transactional
    @DisplayName("should set product price")
    void changeProductPriceByValue_shouldUpdateProductsPrice() throws Exception {
        //given
        String price = "159.99";
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/products/1/set/price")
                .content(asJsonString(price))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should set product discount")
    void changeDiscountValueByPercentage_shouldUpdateProductsDiscount() throws Exception {
        //given
        String discount = "0.30";
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/products/1/set/discount")
                .content(asJsonString(discount))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should set product name")
    void changeProductName_shouldUpdateProductsName() throws Exception {
        //given
        String name = "Lucjan";
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/products/1/set/name")
                .content(asJsonString(name))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should set product type")
    void setTypeOfGivenProduct_shouldUpdateProductsType() throws Exception {
        //given
        String type = "K21";
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/products/1/set/type")
                .content(asJsonString(type))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should set product version")
    void setVersionOfGivenProduct_shouldUpdateProductsVersion() throws Exception {
        //given
        String version = "Hiking";
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/products/1/set/version")
                .content(asJsonString(version))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should set product description")
    void setDescriptionOfGivenProduct_shouldUpdateProductsDescription() throws Exception {
        //given
        String description = "The most strong material ever!";
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/products/1/set/description")
                .content(asJsonString(description))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should delete product")
    void deleteProduct_shouldDeleteProductWithGivenId() throws Exception {
        //given
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should delete products copy")
    void deleteProductsCopy_shouldDeleteProductsCopyWithGivenId() throws Exception {
        //given
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/1/copies/1"))
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
