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
import pl.cichy.RoyalWebStore.model.Order;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class OrderContorllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    @DisplayName("should get all orders")
    void readAllOrders() throws Exception {
        //given
        Order first = new Order(1, "NO");
        Order second = new Order(2, "YES");
        orderRepository.save(first);
        orderRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/orders"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paid", Matchers.is("NO")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paid", Matchers.is("YES")));
    }

    @Test
    @Transactional
    @DisplayName("should get all orders of client with given ID")
    void readAllOrdersByClientId() throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customers/1/orders"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        Order[] order = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Order[].class);
        assertThat(order).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("should post order for customer with given ID")
    void addOrderForCustomerWithGivenId() throws Exception {
        //given
        Order first = new Order();
        first.setPaid("YES");
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/1")
                .content(asJsonString(first))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @Transactional
    @DisplayName("should post copy with given id, of given product, to order with given order id")
    void addCopyOfGivenProductToOrder() throws Exception {
        //given
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/customers/1/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
