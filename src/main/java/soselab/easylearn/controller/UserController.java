package soselab.easylearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soselab.easylearn.model.dto.UserDTO;
import soselab.easylearn.repository.UserRepository;
import soselab.easylearn.service.UserService;

import java.util.List;

/**
 * Created by bernie on 2016/9/8.
 */
@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/pack", method = RequestMethod.GET)
    public List<String> getUserPack(@RequestHeader("user-id") String userId) {
        return userService.getUserPack(userId);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public void login(@RequestBody UserDTO user) {
        LOGGER.info(user.toString());
        userService.login(user.getId(), user.getName());
    }
}
