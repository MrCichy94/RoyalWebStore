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
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    @DisplayName("should get all customers")
    void readAllCustomers_shouldGetAllCustomers() throws Exception {
        //given
        Customer first = new Customer(
                1,
                "TEST_LOGIN",
                "TEST_PASS",
                "Andrzej",
                "Kowalski",
                "Buisnes",
                "USER");
        Customer second = new Customer(
                2,
                "TEST_LOGIN2",
                "TEST_PASS2",
                "Karol",
                "Nowak",
                "Private",
                "USER");
        customerRepository.save(first);
        customerRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Andrzej")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Karol")));
    }

    @Test
    @Transactional
    @DisplayName("should get single customer")
    void readCustomerById_shouldGetSingleCustomer() throws Exception {
        //given
        Customer first = new Customer(
                1,
                "TEST_LOGIN",
                "TEST_PASS",
                "Andrzej",
                "Kowalski",
                "Buisnes",
                "USER");
        customerRepository.save(first);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + first.getCustomerId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        Customer cust = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);
        assertThat(cust).isNotNull();
        assertThat(cust.getCustomerId()).isEqualTo(first.getCustomerId());
    }

    @Test
    @Transactional
    @DisplayName("should post customer")
    void createNewCustomer_shouldAddCustomer() throws Exception {
        //given
        Customer customer = new Customer(
                1,
                "TEST_LOGIN",
                "TEST_PASS",
                "Andrzej",
                "Kowalski",
                "Buisnes",
                "USER");
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/add")
                .content(asJsonString(customer))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @Transactional
    @DisplayName("should delete customer")
    void deleteCustomer_shouldDeleteCustomerWithGivenId() throws Exception {
        //given
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should delete customers order")
    void deleteCustomersOrder_shouldDeleteCustomersOrderWithGivenId() throws Exception {
        //given
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1/orders/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should update customers account data")
    void updateCustomer_shouldUpdateCustomersAccountData() throws Exception {
        //given
        String jsonPatchObjString = "[\n" +
                "    {\n" +
                "        \"op\":\"replace\",\n" +
                "        \"path\":\"/address\",\n" +
                "        \"value\":\n" +
                "        {\n" +
                "            \"country\": \"Polska\",\n" +
                "            \"city\": \"Warszawa\",\n" +
                "            \"streetName\": \"Polna\",\n" +
                "            \"zipCode\": \"30-300\",\n" +
                "            \"doorNumber\": 4\n" +
                "        }\n" +
                "    }\n" +
                "]";
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/customers/1")
                .content(jsonPatchObjString)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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
