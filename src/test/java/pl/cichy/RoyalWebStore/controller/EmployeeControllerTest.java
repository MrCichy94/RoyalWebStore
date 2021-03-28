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
import pl.cichy.RoyalWebStore.model.Employee;
import pl.cichy.RoyalWebStore.model.repository.EmployeeRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Transactional
    @DisplayName("should get all employees")
    void readAllEmployees_shouldGetAllEmployees() throws Exception {
        Employee first = new Employee(
                1,
                "TEST_LOGIN",
                "TEST_PASS",
                "Andrzej",
                "Kowalski",
                "USER");
        Employee second = new Employee(
                2,
                "TEST_LOGIN2",
                "TEST_PASS2",
                "Karol",
                "Nowak",
                "USER");
        employeeRepository.save(first);
        employeeRepository.save(second);
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Andrzej")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Karol")));
    }

    @Test
    @Transactional
    @DisplayName("should get single employee")
    void readEmployeeById_shouldGetSingleEmployee() throws Exception {
        //given
        Employee first = new Employee(
                1,
                "TEST_LOGIN",
                "TEST_PASS",
                "Andrzej",
                "Kowalski",
                "USER");
        employeeRepository.save(first);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + first.getEmployeeId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        Employee employee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);
        assertThat(employee).isNotNull();
        assertThat(employee.getEmployeeId()).isEqualTo(first.getEmployeeId());
    }

    @Test
    @Transactional
    @DisplayName("should post employee")
    void createNewEmployee_shouldCreateNewEmployee() throws Exception {
        //given
        Employee first = new Employee(
                1,
                "TEST_LOGIN",
                "TEST_PASS",
                "Andrzej",
                "Kowalski",
                "USER");
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/employees/add")
                .content(asJsonString(first))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @Transactional
    @DisplayName("should delete employee")
    void deleteEmployee_shouldDeleteEmployeeWithGivenId() throws Exception {
        //given
        //when+then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/employees/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    @DisplayName("should update employees account data")
    void updateEmployee_shouldUpdateEmployeesAccountData() throws Exception {
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
        mockMvc.perform(MockMvcRequestBuilders.patch("/employees/1")
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
