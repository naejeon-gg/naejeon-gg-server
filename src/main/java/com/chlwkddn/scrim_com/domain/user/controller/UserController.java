package com.chlwkddn.scrim_com.domain.user.controller;

import com.chlwkddn.scrim_com.domain.user.dto.req.LoginReq;
import com.chlwkddn.scrim_com.domain.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginReq loginReq , HttpServletResponse response) {
        return userService.login(loginReq,response);
    }
//
//    @GetMapping("/check")
//    public String checkLogin(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies == null) return "쿠키 없음";
//
//        for (Cookie cookie : cookies) {
//            if ("auth_token".equals(cookie.getName())) {
//                if (userService.isValidToken(cookie.getValue())) {
//                    return "로그인 상태입니다 ✅";
//                }
//            }
//        }
//        return "로그인되어 있지 않습니다 ❌";
//    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    cookie.setMaxAge(0);     // 쿠키 만료
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return "로그아웃 성공";
    }
}
