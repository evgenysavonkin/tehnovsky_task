package com.tehnovsky.task.service.impl;

import com.tehnovsky.task.model.User;
import com.tehnovsky.task.repository.UsersRepository;
import com.tehnovsky.task.service.UserService;
import com.tehnovsky.task.util.enums.ExceptionTemplates;
import com.tehnovsky.task.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    public User findById(long id) {
        Optional<User> optionalUser = usersRepository.findById(id);
        return optionalUser.orElseThrow(() ->
                new UserNotFoundException(String.format(ExceptionTemplates.USER_NOT_FOUND_EXCEPTION_FORMAT.getMessage(), id)));
    }
}
