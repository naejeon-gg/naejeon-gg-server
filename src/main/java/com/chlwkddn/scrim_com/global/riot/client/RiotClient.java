package com.chlwkddn.scrim_com.global.riot.client;

import com.chlwkddn.scrim_com.global.riot.properties.RiotProperties;
import com.chlwkddn.scrim_com.global.riot.response.RiotPuuidRes;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RiotClient {
    private final RiotProperties riotProperties;
    private final WebClient webClient = WebClient.create("https://asia.api.riotgames.com");

    public RiotPuuidRes getAccount(String gameName, String tagLine) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
                        .build(gameName, tagLine))
                .header("X-Riot-Token", riotProperties.getKey())   // 핵심 수정
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
                .bodyToMono(RiotPuuidRes.class)
                .block();
    }
}
