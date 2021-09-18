package com.game.service;

import com.game.entity.Player;

public class Updater {
    public static void updateFields(Player player, Player update) {
        if(update.getName() != null) player.setName(update.getName());
        if(update.getTitle() != null) player.setTitle(update.getTitle());
        if(update.getRace() != null) player.setRace(update.getRace());
        if(update.getProfession() != null) player.setProfession(update.getProfession());
        if(update.getBirthday() != null) player.setBirthday(update.getBirthday());
        if(update.getExperience() != null) player.setExperience(update.getExperience());
        if(update.getBanned() != null) player.setBanned(update.getBanned());
        else player.setBanned(false);
    }
}
