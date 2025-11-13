package com.chlwkddn.scrim_com.domain.user.service;

import com.chlwkddn.scrim_com.domain.user.dto.req.LoginReq;
import com.chlwkddn.scrim_com.domain.user.dto.req.RiotPuuidReq;
import com.chlwkddn.scrim_com.global.RiotProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient = WebClient.create("https://asia.api.riotgames.com");
    private final RiotProperties riotProperties;

    public String login( LoginReq loginReq , HttpServletResponse response) {

        String gameName = loginReq.gameName();
        String tagLine = loginReq.tagLine();

        RiotPuuidReq result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
                        .queryParam("api_key", riotProperties.getKey())
                        .build(gameName, tagLine)) // WebClient가 알아서 인코딩
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.out.println("응답 본문: " + errorBody);
                                    return Mono.error(new RuntimeException("클라 오류: " + errorBody));
                                })
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.out.println("응답 본문: " + errorBody);
                                    return Mono.error(new RuntimeException("서버 오류: " + errorBody));
                                })
                )
                .bodyToMono(RiotPuuidReq.class)
                .block();



        if (result==null){
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
