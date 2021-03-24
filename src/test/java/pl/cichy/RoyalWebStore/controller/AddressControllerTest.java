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
import pl.cichy.RoyalWebStore.model.Address;
import pl.cichy.RoyalWebStore.model.repository.AddressRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    @Transactional
    @DisplayName("should get single address")
    void readAddressById_shouldGetSingleAddress() throws Exception {
        //given
        Address first = new Address(
                1,
                "Krakow",
                "Malopolskie",
                "32-300",
                "Krakowska",
                "1");
        addressRepository.save(first);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/addresses/" + first.getAddressId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        Address address = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Address.class);
        assertThat(address).isNotNull();
        assertThat(address.getAddressId()).isEqualTo(first.getAddressId());
    }

    @Test
    @Transactional
    @DisplayName("should get all addresses")
    void readAllAddresses_shouldGetAllAddresses() throws Exception {
        //given
        Address first = new Address(
                1,
                "Krakow",
                "Malopolskie",
                "32-300",
                "Krakowska",
                "1");
        Address second = new Address(
                2,
                "Lublin",
                "Mazowieckie",
                "39-900",
                "Warszawka",
                "2");
        addressRepository.save(first);
        addressRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/addresses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city", Matchers.is("Lublin")));

    }

    @Test
    @Transactional
    @DisplayName("should post address")
    void addAddress() throws Exception {
        //given
        Address address = new Address(
                3,
                "Gdansk",
                "Pomorskie",
                "12-345",
                "Gdanska",
                "3");
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/add")
                .content(asJsonString(address))
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
