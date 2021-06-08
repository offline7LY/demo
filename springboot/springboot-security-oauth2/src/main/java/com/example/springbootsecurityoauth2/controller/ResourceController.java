package com.example.springbootsecurityoauth2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * rest接口同样登录以后才能访问
 */
@RestController
@RequestMapping(value = "/rest")
public class ResourceController {
    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

    /**
     * @data: 2021/6/8-下午4:24
     * @User: zhaozhiwei
     * @method: hello
      * @param authentication :
     * @return: java.lang.String
     * @Description:
     * curl -X GET -H 'Authorization: bearer 49ab5f2d-ca0e-4eae-9527-6d625179d4ba' -i http://localhost:8080/rest/hello
     */
    @GetMapping("/hello")
    public String hello(Authentication authentication){
        log.info("resource: user {}", authentication);
        return "hello:"+ authentication.getName();
    }

    @GetMapping("/sayhello")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String sayhello(Authentication authentication){
        log.info("resource: user {} has role ROLE_USER", authentication);
        return "sayhello:"+ authentication.getName();
    }

    /**
     * @EnableGlobalMethodSecurity(prePostEnabled = true) 需要打开
     * There was an unexpected error (type=Forbidden, status=403).
     * Forbidden
     * @param authentication
     * @return
     */
    @GetMapping("/sayhello1")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String sayhello1(Authentication authentication){
        log.info("resource: user {} has role ROLE_ADMIN", authentication);
        return "sayhello:"+ authentication.getName();
    }

}

