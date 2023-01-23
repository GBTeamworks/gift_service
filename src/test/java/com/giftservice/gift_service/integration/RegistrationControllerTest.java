package com.giftservice.gift_service.integration;

import com.giftservice.gift_service.controllers.RegistrationController;
import com.giftservice.gift_service.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"dml-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registrationPageTest() throws Exception {

        this.mockMvc.perform(
                        get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "Регистрация")))
                .andExpect(xpath("//*[@id=\"username\"]").exists())
                .andExpect(xpath("//*[@id=\"exampleInputEmail1\"]").exists())
                .andExpect(xpath("//*[@id=\"birthdate\"]").exists())
                .andExpect(xpath("//*[@id=\"exampleInputPassword1\"]").exists())
                .andExpect(xpath("/html/body/div/form/button").exists());
    }

    @Test
    public void registrationUserTest() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUsername("newTestUser");
        userDto.setEmail("testtesttest123@mail.ru");
        userDto.setBirthdate(String.valueOf(LocalDate.now()));
        userDto.setPassword("testPass");

        BindingResult result = mock(BindingResult.class);
        registrationController.registration(userDto, result, null);

        this.mockMvc.perform(formLogin().user("newTestUser").password("testPass"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
