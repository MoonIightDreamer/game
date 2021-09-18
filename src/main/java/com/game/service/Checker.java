package com.game.service;

import com.game.entity.Player;

public class Checker {
    private static Player player;

    public static boolean check(Player playerToCheck) {
        player = playerToCheck;
        return checkNulls() && checkName() && checkTitle() && checkExperience() && checkBirthday();
    }

    private static boolean checkNulls() {
        return player.getName() != null && player.getTitle() != null &&
                player.getRace() != null && player.getProfession() != null &&
                player.getBirthday() != null && player.getExperience() != null;
    }

    private static boolean checkName() {
        return player.getName().length() > 0 && player.getName().length() < 13;
    }

    private static boolean checkTitle() {
        return player.getTitle().length() < 31;
    }

    private static boolean checkExperience() {
        return player.getExperience() >= 0 && player.getExperience() <= 10_000_000;
    }

    private static boolean checkBirthday() {
        return player.getBirthday().getTime() > 0 && player.getBirthday().getYear() >= 100
                && player.getBirthday().getYear() <= 1100;
    }
}
