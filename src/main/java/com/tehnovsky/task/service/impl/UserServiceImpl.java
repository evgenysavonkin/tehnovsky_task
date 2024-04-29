package com.tehnovsky.task.service.impl;

import com.tehnovsky.task.model.User;
import com.tehnovsky.task.repository.UsersRepository;
import com.tehnovsky.task.service.UserService;
import com.tehnovsky.task.util.enums.ExceptionTemplates;
import com.tehnovsky.task.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.tehnovsky.task.util.ValuesChecker.checkUserId;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    @Override
    @Transactional(readOnly = true)
    public User findById(long id) {
        log.info("UserServiceImpl findById start");
        checkUserId(id);
        Optional<User> optionalUser = usersRepository.findById(id);
        log.info("UserServiceImpl findById end");
        return optionalUser.orElseThrow(() ->
                new UserNotFoundException(String.format(ExceptionTemplates.USER_NOT_FOUND_EXCEPTION_FORMAT.getMessage(), id)));
    }
}
