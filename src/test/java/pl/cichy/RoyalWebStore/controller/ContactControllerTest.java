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
import pl.cichy.RoyalWebStore.model.Contact;
import pl.cichy.RoyalWebStore.model.repository.ContactRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ContactRepository contactRepository;

    @Test
    @Transactional
    @DisplayName("should get single contact")
    void readContactById_shouldGetSingleContact() throws Exception {
        //given
        Contact first = new Contact(
                1,
                "111444555",
                "mojemail@onet.pl");
        contactRepository.save(first);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/contacts/" + first.getContactId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        Contact contact = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Contact.class);
        assertThat(contact).isNotNull();
        assertThat(contact.getContactId()).isEqualTo(first.getContactId());
    }

    @Test
    @Transactional
    @DisplayName("should get all contacts")
    void readAllContacts_shouldGetAllContacts() throws Exception {
        //given
        Contact first = new Contact(
                1,
                "111444555",
                "mojemail@onet.pl");
        Contact second = new Contact(
                2,
                "111222333",
                "moj@onet.pl");
        contactRepository.save(first);
        contactRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/contacts"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].emailAddress", Matchers.is("moj@onet.pl")));

    }

    @Test
    @Transactional
    @DisplayName("should post contact")
    void addContact() throws Exception {
        //given
        Contact adr = new Contact(
                7,
                "775115866",
                "noel@onet.pl");
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/contacts/add")
                .content(asJsonString(adr))
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
