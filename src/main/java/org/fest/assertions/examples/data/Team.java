package org.fest.assertions.examples.data;

import java.util.List;

public class Team {

  private List<Player> players;

  public Team(List<Player> players) {
    super();
    this.players = players;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

}
