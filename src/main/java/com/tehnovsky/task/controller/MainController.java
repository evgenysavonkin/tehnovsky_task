package com.tehnovsky.task.controller;

import com.tehnovsky.task.dto.SendingMoneyToUserDTO;
import com.tehnovsky.task.dto.TopUpUserBalanceDTO;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.service.MoneyActionsService;
import com.tehnovsky.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final MoneyActionsService moneyActionsService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/top_up_balance")
    public ResponseEntity<?> topUpUserBalance(@RequestBody TopUpUserBalanceDTO userBalanceDTO) {
        moneyActionsService.topUpUserBalance(userBalanceDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send_money")
    public ResponseEntity<?> sendMoneyToAnotherUser(@RequestBody SendingMoneyToUserDTO moneyToUserDTO) {
        moneyActionsService.sendMoneyToAnotherUser(moneyToUserDTO);
        return ResponseEntity.ok().build();
    }
}
