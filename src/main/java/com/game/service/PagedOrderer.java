package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PagedOrderer {
    public static List<Player> orderAndPage(List<Player> players, PlayerOrder order, Integer pageNumber,
                                     Integer pageSize) {
        players = order(players, order);
        players = page(players, pageNumber, pageSize);
        return players;
    }

    public static List<Player> order(List<Player> players, PlayerOrder order) {
        switch (order) {
            case NAME:
                return players.stream().sorted(Comparator.comparing(Player::getName)).collect(Collectors.toList());
            case EXPERIENCE:
                return players.stream().sorted(Comparator.comparing(Player::getExperience)).collect(Collectors.toList());
            case BIRTHDAY:
                return players.stream().sorted(Comparator.comparing(Player::getBirthday)).collect(Collectors.toList());
            case LEVEL:
                return players.stream().sorted(Comparator.comparing(Player::getLevel)).collect(Collectors.toList());
            default:
                return players.stream().sorted(Comparator.comparing(Player::getId)).collect(Collectors.toList());
        }
    }

    public static List<Player> page(List<Player> players, Integer pageNumber, Integer pageSize) {
        int leftBorder = pageNumber * pageSize;
        int rightBorder = (pageNumber + 1) * pageSize;
        if (rightBorder > players.size()) rightBorder = players.size();
        return players.subList(leftBorder, rightBorder);
    }
}
