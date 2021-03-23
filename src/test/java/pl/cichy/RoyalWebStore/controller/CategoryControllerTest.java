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
import pl.cichy.RoyalWebStore.model.Category;
import pl.cichy.RoyalWebStore.model.repository.CategoryRepository;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    @DisplayName("should get all categories")
    void readAllCategories_shouldGetAllCategories() throws Exception {
        //given
        Category first = new Category("Ubrania");
        Category second = new Category("Obuwie");
        categoryRepository.save(first);
        categoryRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/categories"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoryName", Matchers.is("Ubrania")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].categoryName", Matchers.is("Obuwie")));
    }

    @Test
    @Transactional
    @DisplayName("should post category")
    void addCategory() throws Exception {
        //given
        Category cat = new Category("TestCategory");
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/products/1/category/add")
                .content(asJsonString(cat))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
