package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImplementation implements PlayerService{
    @Autowired
    PlayerRepository playerRepository;

    @Override
    public Player getById(Long id) {
        return playerRepository.findById(id).get();
    }

    @Override
    public void save(Player player) {
        player.setLevel((int)((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100));
        player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) -
                player.getExperience());
        playerRepository.save(player);
    }

    @Override
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public List<Player> getAll() {
        return playerRepository.findAll();
    }
}
