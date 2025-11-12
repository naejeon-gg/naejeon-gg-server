package com.chlwkddn.scrim_com.domain;
import java.util.*;
import java.util.stream.Collectors;

public class TeamBalancer {

    public static class Player {
        String name;
        double rating;
        String mainPosition;
        String subPosition;

        public Player(String name, double rating, String mainPosition, String subPosition) {
            this.name = name;
            this.rating = rating;
            this.mainPosition = mainPosition;
            this.subPosition = subPosition;
        }

        @Override
        public String toString() {
            return String.format("%s (%.1f, %s/%s)", name, rating, mainPosition, subPosition);
        }
    }

    public static class Team {
        List<Player> members = new ArrayList<>();

        public void add(Player p) {
            members.add(p);
        }

        public double getAverageRating() {
            return members.stream().mapToDouble(p -> p.rating).average().orElse(0);
        }

        public boolean hasPosition(String pos) {
            return members.stream().anyMatch(p -> p.mainPosition.equals(pos));
        }

        @Override
        public String toString() {
            return members.stream().map(Player::toString).collect(Collectors.joining(", "));
        }
    }

    public static void main(String[] args) {
        List<Player> players = List.of(
                new Player("동우", 2100, "TOP", "JUNGLE"),
                new Player("해성", 2200, "MID", "ADC"),
                new Player("세혁", 2400, "JUNGLE", "TOP"),
                new Player("민준", 2000, "ADC", "MID"),
                new Player("승원", 1900, "SUPPORT", "ADC"),
                new Player("장우", 1500, "JUNGLE", "MID"),
                new Player("현우", 1700, "ADC", "SUPPORT"),
                new Player("시원", 1600, "MID", "TOP"),
                new Player("세빈", 2300, "TOP", "MID"),
                new Player("리온", 2500, "SUPPORT", "JUNGLE")
        );

        Map<String, List<Player>> grouped = players.stream()
                .sorted(Comparator.comparingDouble(p -> -p.rating))
                .collect(Collectors.groupingBy(p -> p.mainPosition));

        Team teamA = new Team();
        Team teamB = new Team();

        // 1️⃣ 포지션별로 번갈아가며 배분
        for (String pos : List.of("TOP", "JUNGLE", "MID", "ADC", "SUPPORT")) {
            List<Player> group = grouped.getOrDefault(pos, new ArrayList<>());
            for (int i = 0; i < group.size(); i++) {
                if (teamA.members.size() < 5 && (i % 2 == 0)) {
                    teamA.add(group.get(i));
                } else if (teamB.members.size() < 5) {
                    teamB.add(group.get(i));
                }
            }
        }

        // 2️⃣ 남은 인원 처리 (부포지션 기반)
        List<Player> unassigned = new ArrayList<>(players);
        unassigned.removeAll(teamA.members);
        unassigned.removeAll(teamB.members);

        for (Player p : unassigned) {
            if (!teamA.hasPosition(p.subPosition) && teamA.members.size() < 5) {
                teamA.add(p);
            } else if (!teamB.hasPosition(p.subPosition) && teamB.members.size() < 5) {
                teamB.add(p);
            } else {
                // 자리가 없으면 인원 적은 팀에 배정
                if (teamA.members.size() <= teamB.members.size()) teamA.add(p);
                else teamB.add(p);
            }
        }

        // 출력
        System.out.println("===== 팀 A =====");
        System.out.println(teamA);
        System.out.println("평균 평점: " + teamA.getAverageRating());

        System.out.println("\n===== 팀 B =====");
        System.out.println(teamB);
        System.out.println("평균 평점: " + teamB.getAverageRating());

        //todo 매번 랜덤으로? 팀바꾸기 기능 추가
    }
}
