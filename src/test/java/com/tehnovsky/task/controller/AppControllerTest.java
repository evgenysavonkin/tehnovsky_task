package com.tehnovsky.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tehnovsky.task.model.Account;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.service.UserService;
import com.tehnovsky.task.util.db_utils.DataCleaner;
import com.tehnovsky.task.util.db_utils.DataInitializer;
import com.tehnovsky.task.util.enums.Currency;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private DataCleaner dataCleaner;

    @Resource
    private DataInitializer dataInitializer;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    public static void startDocker() {
        postgres.start();
    }

    @AfterAll
    public static void stopDocker() {
        postgres.stop();
    }

    @BeforeEach
    public void initDataInDB() {
        dataCleaner.dropTablesIfExist(jdbcTemplate);
        dataInitializer.initData(jdbcTemplate);
    }

    @AfterEach
    public void destroyDataInDB() {
        dataCleaner.dropTablesIfExist(jdbcTemplate);
    }

    @Test
    void getUserById() throws Exception {
        long userId = 1;
        var result = mockMvc.perform(get("/api/v1/users/" + userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User expectedUser = userService.findById(1);
        ObjectMapper objectMapper = new ObjectMapper();
        User actualUser = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        Assertions.assertEquals(expectedUser.toString(), actualUser.toString());
    }

    @Test
    void topUpUserBalance() throws Exception {
        String topUpRequest = "{\n" +
                "    \"userId\": 2,\n" +
                "    \"amountOfMoney\": 100,\n" +
                "    \"currency\": \"BYN\"\n" +
                "}";

        mockMvc.perform(post("/api/v1/users/top_up_balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topUpRequest))
                .andDo(print())
                .andExpect(status().isOk());

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
    void sendMoneyToAnotherUser() throws Exception {
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
                .andExpect(status().isOk());

        User senderUser = userService.findById(1);
        Optional<Account> senderAccountOptional = senderUser.getAccounts().stream()
                .filter(account -> account.getCurrency() == Currency.BYN)
                .findFirst();

        User receiverUser = userService.findById(2);
        Optional<Account> receiverAccoutOptional = receiverUser.getAccounts().stream()
                .filter(account -> account.getCurrency() == Currency.BYN)
                .findFirst();

        if (senderAccountOptional.isEmpty() || receiverAccoutOptional.isEmpty()) {
            Assertions.fail("User account not found");
        }

        Assertions.assertEquals(0, senderAccountOptional.get().getBalance().doubleValue());
        Assertions.assertEquals(10.34, receiverAccoutOptional.get().getBalance().doubleValue());
    }
}