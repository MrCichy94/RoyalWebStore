package pl.cichy.RoyalWebStore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.cichy.RoyalWebStore.model.Address;
import pl.cichy.RoyalWebStore.model.repository.AddressRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
                "Warszawa",
                "Mazowieckie",
                "39-900",
                "Warszawka",
                "2");
        addressRepository.save(first);
        addressRepository.save(second);

        var mockAddressRepository = mock(AddressRepository.class);

        //when
        when(mockAddressRepository.findAll()).thenReturn(Lists.newArrayList(first, second));

        //then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/addresses"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city", Matchers.is("Warszawa")));
    }
}
