package com.giftservice.gift_service.integration;

import com.giftservice.gift_service.controllers.GiftController;
import com.giftservice.gift_service.controllers.UsersController;
import com.giftservice.gift_service.dto.GiftDto;
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
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithUserDetails("TestUser1")
@TestPropertySource("/application-test.properties")
@Sql(value = {"dml-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
public class GiftControllerTest {

    @Autowired
    private GiftController giftController;

    @Autowired
    private UsersController usersController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void giftsPageTest() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUsername("TestUser2");
        userDto.setBirthdate("2022-12-02");
        userDto.setEmail("testUser2@mail.ru");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        usersController.addFriend(userDto, auth);

        this.mockMvc.perform(
                        get("/gifts"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[2]/div/div[1]").exists())
                .andExpect(content().string(containsString(
                        "TestUser2 хочет этот подарок")));
    }

    @Test
    public void willGiveTest() throws Exception {

        GiftDto giftDto = new GiftDto();
        giftDto.setTitle("Test Gift2");
        giftController.willGive(giftDto, "TestUser2");

        this.mockMvc.perform(
                        get("/lk/i-will-give"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[2]/span").doesNotExist());
    }
}
