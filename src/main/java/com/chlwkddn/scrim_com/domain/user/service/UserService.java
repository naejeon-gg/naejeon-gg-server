package com.chlwkddn.scrim_com.domain.user.service;

import com.chlwkddn.scrim_com.domain.user.dto.req.LoginReq;
import com.chlwkddn.scrim_com.global.riot.client.RiotClient;
import com.chlwkddn.scrim_com.global.riot.response.RiotPuuidRes;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RiotClient riotClient;

    public String login(LoginReq loginReq, HttpServletResponse response) {

        String gameName = loginReq.gameName();
        String tagLine = loginReq.tagLine();

        RiotPuuidRes result = riotClient.getAccount(gameName, tagLine);

        if (result == null) {
            throw new RuntimeException("npe");
        }

        String token = result.puuid();
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setHttpOnly(true);   // JS 접근 불가
        cookie.setSecure(false);    // HTTPS만 사용할 경우 true
        cookie.setPath("/");        // 전체 경로에서 유효
        cookie.setMaxAge(Integer.MAX_VALUE);  // 1시간 유효
        response.addCookie(cookie);

        return token;
    }

}

//https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}?api_key=
