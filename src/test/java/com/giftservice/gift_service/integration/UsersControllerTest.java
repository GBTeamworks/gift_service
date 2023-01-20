package com.giftservice.gift_service.integration;

import com.giftservice.gift_service.controllers.PersonalAccountController;
import com.giftservice.gift_service.controllers.UsersController;
import com.giftservice.gift_service.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithUserDetails("TestUser1")
@TestPropertySource("/application-test.properties")
@Sql(value = {"dml-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private UsersController usersController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void usersPageTest() throws Exception {

        this.mockMvc.perform(
                        get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "Страница пользователей")))
                .andExpect(xpath("/html/body/div[2]/div/table/thead/tr/th[1]").exists())
                .andExpect(xpath("/html/body/div[2]/div/table/thead/tr/th[2]").exists())
                .andExpect(xpath("/html/body/div[2]/div/table/thead/tr/th[3]").exists())
                .andExpect(xpath("/html/body/div[2]/div/table/thead/tr/th[4]").exists());
    }

    @Test
    public void addFriendTest() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUsername("TestUser2");
        userDto.setBirthdate("2022-12-02");
        userDto.setEmail("testUser2@mail.ru");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        this.mockMvc.perform(formLogin().user("TestUser1").password("TestUser1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        this.mockMvc.perform(
                        get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body//form/button[contains(text(), 'Добавить')]").exists());


        usersController.addFriend(userDto, auth);

        this.mockMvc.perform(
                        get("/lk/friends"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "TestUser2")))
                .andExpect(content().string(containsString(
                        "testUser2@mail.ru")));
    }
}
