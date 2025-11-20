package com.chlwkddn.scrim_com.domain.game.presentation;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameDataController {

    @PostMapping("/data")
    public String receiveGameData(@RequestBody String body) {

        System.out.println("===== 들어온 게임 데이터 =====");
        System.out.println(body);
        System.out.println("==========================");

        return "ok";
    }
}