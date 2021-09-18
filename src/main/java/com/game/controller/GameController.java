package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/players")
public class GameController {

    @Autowired
    private PlayerService playerService;

    public boolean idCheck(Long id) {
        return id != null && id > 0;
    }

    public boolean idPresent(Long id) {
        Player playerToCheck = playerService.getAll()
                .stream().filter(player -> player.getId().equals(id)).findFirst().orElse(null);
        return playerToCheck != null;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long playerId) {
        if (playerId == null || !idCheck(playerId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!idPresent(playerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Player player = this.playerService.getById(playerId);

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        HttpHeaders headers = new HttpHeaders();
        if (player == null || !Checker.check(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        player.setId((long)playerService.getAll().size() + 1);
        this.playerService.save(player);

        return new ResponseEntity<>(player, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") Long id,
    @RequestBody Player updatedPlayer) {
        HttpHeaders headers = new HttpHeaders();

        if (id == null || !idCheck(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!idPresent(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Player player = playerService.getById(id);
        Updater.updateFields(player, updatedPlayer);
        player.setId(id);
        if(!Checker.check(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.playerService.save(player);

        return new ResponseEntity<>(player, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") Long id) {
        if(!idCheck(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!idPresent(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Player player = playerService.getById(id);

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.playerService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPlayersCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel
    ) {
        List<Player> players = this.playerService.getAll();
        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        players = Filter.filter(players, name, title, race, profession,
                after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
        Filter.setCount(players.size());
        Integer playersCount = Filter.getCount();

        return new ResponseEntity<>(playersCount, HttpStatus.OK);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getAllPlayers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "order", defaultValue = "ID") PlayerOrder order) {
        List<Player> players = this.playerService.getAll();
        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        players = Filter.filter(players, name, title, race, profession,
                after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
        Filter.setCount(players.size());
        players = PagedOrderer.orderAndPage(players, order, pageNumber, pageSize);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
}
