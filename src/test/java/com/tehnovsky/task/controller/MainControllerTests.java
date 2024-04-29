package com.tehnovsky.task.controller;

import com.tehnovsky.task.model.Account;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.service.UserService;
import com.tehnovsky.task.util.enums.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/data.sql")
@AutoConfigureMockMvc
class MainControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;

    @Autowired
    private UserService userService;

    @Test
    void getUserWithCorrectId_returnsUser_test() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bob")));
    }

    @Test
    void getUser_with_incorrectId_sendsMessageAndStatus400_test() throws Exception {
        mockMvc.perform(get("/api/v1/users/-2"))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(content().string(containsString("User id can't be <= 0. Actual is -2")));
    }

    @Test
    void topUpUserBalance_test() throws Exception {
        String topUpRequest = "{\n" +
                "    \"userId\": 2,\n" +
                "    \"amountOfMoney\": 100,\n" +
                "    \"currency\": \"BYN\"\n" +
                "}";
        mockMvc.perform(post("/api/v1/users/top_up_balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topUpRequest))
                .andDo(print())
                .andReturn();

        User user = userService.findById(2);
        Optional<Account> userAccountOptional = user.getAccounts().stream()
                .filter(account -> account.getCurrency() == Currency.BYN)
                .findFirst();

        if (userAccountOptional.isEmpty()) {
            Assertions.fail("User account not found");
        }

        Assertions.assertEquals(100, userAccountOptional.get().getBalance().doubleValue());
    }

    @Test
    void sendMoneyToAnotherUser_test() throws Exception {
        String sendMoneyRequest = "{\n" +
                "    \"senderUserId\": 1,\n" +
                "    \"senderCurrency\": \"BYN\",\n" +
                "    \"receiverUserId\": 2,\n" +
                "    \"amount\": 10.34\n" +
                "}";

        mockMvc.perform(post("/api/v1/users/send_money")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sendMoneyRequest))
                .andDo(print())
                .andReturn();

        User user = userService.findById(2);
        Optional<Account> userAccountOptional = user.getAccounts().stream()
                .filter(account -> account.getCurrency() == Currency.BYN)
                .findFirst();

        if (userAccountOptional.isEmpty()) {
            Assertions.fail("User account not found");
        }

        Assertions.assertEquals(10.34, userAccountOptional.get().getBalance().doubleValue());

    }
}

