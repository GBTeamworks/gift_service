package com.giftservice.gift_service.integration;

import com.giftservice.gift_service.controllers.WishController;
import com.giftservice.gift_service.dto.GiftDto;
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
public class WishControllerTest {

    @Autowired
    private WishController wishController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void wishesShowPageTest() throws Exception {

        this.mockMvc.perform(
                        get("/wishes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "Страница желаний")))
                .andExpect(content().string(containsString(
                        "Список моих желаний")))
                .andExpect(xpath("/html/body/div[2]/a").exists());
    }

    @Test
    public void addWishShowPageTest() throws Exception {

        this.mockMvc.perform(
                        get("/wishes/add-wish"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "Страница добавления желания")))
                .andExpect(xpath("//*[@id=\"title\"]").exists())
                .andExpect(xpath("//*[@id=\"exampleFormControlTextarea1\"]").exists())
                .andExpect(xpath("/html/body//button[contains(text(),'Добавить')]").exists());
    }

    @Test
    public void addNewWishTest() throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        GiftDto giftDto = new GiftDto();
        giftDto.setTitle("TestWish");
        giftDto.setDescription("Test description");
        wishController.addNewWish(giftDto, auth);

        this.mockMvc.perform(
                        get("/wishes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "TestWish")))
                .andExpect(content().string(containsString(
                        "Test description")))
                .andExpect(xpath("/html/body//button[contains(text(),'Удалить')]").exists());
    }

    @Test
    public void deleteWishTest() throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        GiftDto giftDto = new GiftDto();
        giftDto.setTitle("Test Gift1");
        giftDto.setDescription("Test1Test1Test1");

        wishController.deleteWish(giftDto, auth);

        this.mockMvc.perform(
                        get("/wishes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "Список подарков пуст")))
                .andExpect(xpath("/html/body//button[contains(text(),'Удалить')]").doesNotExist());
    }
}