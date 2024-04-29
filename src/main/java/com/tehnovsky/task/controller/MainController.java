package com.tehnovsky.task.controller;

import com.tehnovsky.task.dto.SendingMoneyToUserDTO;
import com.tehnovsky.task.dto.TopUpUserBalanceDTO;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.service.MoneyActionsService;
import com.tehnovsky.task.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final UserService userService;
    private final MoneyActionsService moneyActionsService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        log.info("Main controller getUserById start: id = {}", id);
        User user = userService.findById(id);
        log.info("Main controller getUserById end");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/top_up_balance")
    public ResponseEntity<String> topUpUserBalance(@RequestBody TopUpUserBalanceDTO userBalanceDTO) {
        log.info("Main controller topUpUserBalance start: userBalanceDTO = {}", userBalanceDTO);
        moneyActionsService.topUpUserBalance(userBalanceDTO);
        log.info("Main controller topUpUserBalance end");
        return ResponseEntity.ok("Balance topped up successfully");
    }

    @PostMapping("/send_money")
    public ResponseEntity<String> sendMoneyToAnotherUser(@RequestBody SendingMoneyToUserDTO moneyToUserDTO) {
        log.info("Main controller sendMoneyToAnotherUser start: moneyToUserDTO = {}", moneyToUserDTO);
        moneyActionsService.sendMoneyToAnotherUser(moneyToUserDTO);
        log.info("Main controller sendMoneyToAnotherUser end");
        return ResponseEntity.ok("Money sent successfully");
    }
}
