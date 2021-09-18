package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;
import java.util.stream.Collectors;

public class Filter {
    private static Integer count;

    public static Integer getCount() {
        return count;
    }

    public static void setCount(Integer count) {
        Filter.count = count;
    }

    public static List<Player> filter(List<Player> players, String name, String title,
                                      Race race, Profession profession, Long after,
                                      Long before, Boolean banned, Integer minExperience,
                                      Integer maxExperience, Integer minLevel, Integer maxLevel) {
        players = filterByName(players, name);
        players = filterByTitle(players, title);
        players = filterByRace(players, race);
        players = filterByProfession(players, profession);
        players = filterByBirthday(players, after, before);
        players = filterByBan(players, banned);
        players = filterByExperience(players, minExperience, maxExperience);
        players = filterByLevel(players, minLevel, maxLevel);
        return players;
    }

    public static List<Player> filterByName(List<Player> players, String name) {
        if(name != null) {
            return players.stream().
                    filter(player -> (player.getName().contains(name))).collect(Collectors.toList());
        } else {
            return players;
        }
    }

    public static List<Player> filterByTitle(List<Player> players, String title) {
        if(title != null) {
            return players.stream().
                    filter(player -> (player.getTitle().contains(title))).collect(Collectors.toList());
        } else {
            return players;
        }
    }

    public static List<Player> filterByRace(List<Player> players, Race race) {
        if(race != null) {
            return players.stream().
                    filter(player -> (player.getRace().equals(race))).collect(Collectors.toList());
        } else {
            return players;
        }
    }

    public static List<Player> filterByProfession(List<Player> players, Profession profession) {
        if(profession != null) {
            return players.stream().
                    filter(player -> (player.getProfession().equals(profession))).collect(Collectors.toList());
        } else {
            return players;
        }
    }

    public static List<Player> filterByBirthday(List<Player> players, Long after, Long before) {
        if(after != null && before != null) {
            return players.stream().
                    filter(player -> (player.getBirthday().getTime() >= after))
                    .filter(player -> (player.getBirthday().getTime() <= before))
                    .collect(Collectors.toList());
        }
        else if(after != null) {
            return players.stream().
                    filter(player -> (player.getBirthday().getTime() >= after))
                    .collect(Collectors.toList());
        }
        else if(before != null) {
            return players.stream().
                    filter(player -> (player.getBirthday().getTime() <= before))
                    .collect(Collectors.toList());
        }
        else return players;
    }

    public static List<Player> filterByBan(List<Player> players, Boolean banned) {
        if(banned != null) {
            return players.stream()
                    .filter(player -> player.getBanned().equals(banned))
                    .collect(Collectors.toList());
        } else return players;
    }

    public static List<Player> filterByExperience(List<Player> players,
                                                  Integer minExperience, Integer maxExperience) {
        if(minExperience != null && maxExperience != null) {
            return players.stream().
                    filter(player -> (player.getExperience() >= minExperience))
                    .filter(player -> (player.getExperience() <= maxExperience))
                    .collect(Collectors.toList());
        }
        else if(minExperience != null) {
            return players.stream().
                    filter(player -> (player.getExperience() >= minExperience))
                    .collect(Collectors.toList());
        }
        else if(maxExperience != null) {
            return players.stream().
                    filter(player -> (player.getExperience() <= maxExperience))
                    .collect(Collectors.toList());
        }
        else return players;
    }

    public static List<Player> filterByLevel(List<Player> players, Integer minLevel, Integer maxLevel) {
        if(minLevel != null && maxLevel != null) {
            return players.stream().
                    filter(player -> (player.getLevel() >= minLevel))
                    .filter(player -> (player.getLevel() <= maxLevel))
                    .collect(Collectors.toList());
        }
        else if(minLevel != null) {
            return players.stream().
                    filter(player -> (player.getLevel() >= minLevel))
                    .collect(Collectors.toList());
        }
        else if(maxLevel != null) {
            return players.stream().
                    filter(player -> (player.getLevel() <= maxLevel))
                    .collect(Collectors.toList());
        }
        else return players;
    }
}
