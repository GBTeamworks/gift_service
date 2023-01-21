package com.giftservice.gift_service.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithUserDetails("TestUser1")
@TestPropertySource("/application-test.properties")
@Sql(value = {"dml-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
public class PersonalAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mainLkPageTest() throws Exception {

        this.mockMvc.perform(
                        get("/lk"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "Личный кабинет")))
                .andExpect(xpath("/html/body/div[2]/a[1]").exists())
                .andExpect(xpath("/html/body/div[2]/a[2]").exists())
                .andExpect(xpath("/html/body/div[2]/a[3]").exists())
                .andExpect(xpath("/html/body/div[2]/a[4]").exists());
    }

    @Test
    public void userInfoPageTest() throws Exception {

        this.mockMvc.perform(
                        get("/lk/user-info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("//*[@id=\"username\"]").exists())
                .andExpect(xpath("//*[@id=\"exampleInputEmail1\"]").exists())
                .andExpect(xpath("//*[@id=\"birthdate\"]").exists());
    }

    @Test
    public void friendsPageTest() throws Exception {

        this.mockMvc.perform(
                        get("/lk/friends"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "Друзья")));
    }

    @Test
    public void iWillGivePageTest() throws Exception {

        this.mockMvc.perform(
                        get("/lk/i-will-give"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "Я буду дарить")));
    }
}