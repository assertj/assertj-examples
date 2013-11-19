package org.assertj.examples.data.service;

import org.assertj.examples.data.BasketBallPlayer;

import java.util.List;

public class GameService {

  private final TeamManager teamManager;

  public GameService(TeamManager teamManager) {
    this.teamManager = teamManager;
  }

  public boolean play(){

    List<BasketBallPlayer> players = teamManager.getPlayers();

    return !players.isEmpty();
  }

}
