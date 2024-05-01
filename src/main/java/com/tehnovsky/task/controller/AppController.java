package com.tehnovsky.task.controller;

import com.tehnovsky.task.dto.SendingMoneyToUserDTO;
import com.tehnovsky.task.dto.TopUpUserBalanceDTO;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.service.MoneyActionsService;
import com.tehnovsky.task.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class AppController {
    private final UserService userService;
    private final MoneyActionsService moneyActionsService;

    @Operation(
            tags = "Get user by id",
            description = "Endpoint for getting user by id",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Invalid user id", responseCode = "400"),
            },
            parameters = @Parameter(
                    name = "id",
                    description = "id of user that you want to get",
                    example = "1",
                    required = true)
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        log.info("Main controller getUserById start: id = {}", id);
        User user = userService.findById(id);
        log.info("Main controller getUserById end");
        return ResponseEntity.ok(user);
    }

    @Operation(
            tags = "Top up user balance",
            description = "Endpoint for topping up user balance",
            responses = {
                    @ApiResponse(description = "Balance topped up successfully", responseCode = "200"),
                    @ApiResponse(description = "Invalid arguments", responseCode = "400"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = """
                            DTO to perform this action.
                            Note that id and amount of money can't be <= 0.
                            Also consider currency to be acceptable.
                            """,
                    content = @Content(
                            schema = @Schema(implementation = TopUpUserBalanceDTO.class),
                            examples = @ExampleObject(
                                    name = "Example of valid json",
                                    value = """
                                            {
                                                "userId": 2,
                                                "amountOfMoney": 100,
                                                "currency": "BYN"
                                            }
                                            """
                            )
                    ),
                    required = true
            )

    )
    @PostMapping("/top_up_balance")
    public ResponseEntity<String> topUpUserBalance(@RequestBody TopUpUserBalanceDTO userBalanceDTO) {
        log.info("Main controller topUpUserBalance start: userBalanceDTO = {}", userBalanceDTO);
        moneyActionsService.topUpUserBalance(userBalanceDTO);
        log.info("Main controller topUpUserBalance end");
        return ResponseEntity.ok("Balance topped up successfully");
    }

    @Operation(
            tags = "Send money to another user",
            description = "Endpoint for sending money between users",
            responses = {
                    @ApiResponse(description = "Money sent successfully", responseCode = "200"),
                    @ApiResponse(description = "Invalid arguments", responseCode = "400"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = """
                            DTO to perform this action.
                            Note that id of users and amount of money can't be <= 0.
                            Also consider currency to be acceptable.""",
                    content = @Content(
                            schema = @Schema(implementation = SendingMoneyToUserDTO.class),
                            examples = @ExampleObject(
                                    name = "Example of valid json",
                                    value = """
                                            {
                                                "senderUserId": 2,
                                                "senderCurrency": "USD",
                                                "receiverUserId": 1,
                                                "amount": 131
                                            }
                                            """
                            )
                    ),
                    required = true
            )
    )
    @PostMapping("/send_money")
    public ResponseEntity<String> sendMoneyToAnotherUser(@RequestBody SendingMoneyToUserDTO moneyToUserDTO) {
        log.info("Main controller sendMoneyToAnotherUser start: moneyToUserDTO = {}", moneyToUserDTO);
        moneyActionsService.sendMoneyToAnotherUser(moneyToUserDTO);
        log.info("Main controller sendMoneyToAnotherUser end");
        return ResponseEntity.ok("Money sent successfully");
    }
}
