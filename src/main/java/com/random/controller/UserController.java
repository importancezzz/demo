package com.random.controller;

import com.random.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Log4j2
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users/{seed}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity getUserBySeed(@PathVariable String seed) throws Exception {
        log.info("[getUserBySeed] seed : {}" ,seed);
        return userService.getUserBySeed(seed);
    }
}
