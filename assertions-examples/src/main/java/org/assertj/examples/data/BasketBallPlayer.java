/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2016 the original author or authors.
 */
package org.assertj.examples.data;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import org.assertj.examples.exception.NameException;

/**
 * @author Joel Costigliola
 */
public class BasketBallPlayer {

  private Name name;
  public double size;
  private float weight;
  private int pointsPerGame;
  private int assistsPerGame;
  private int reboundsPerGame;
  private String team;
  private boolean rookie;
  private List<BasketBallPlayer> teamMates = new ArrayList<BasketBallPlayer>();
  private List<int[]> points = new ArrayList<>();

  public BasketBallPlayer(Name name, String team) {
    setName(name);
    setTeam(team);
  }

  public Name getName() throws NameException {
    if (name == null) {
      throw new NameException(null);
    }
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }
  
  public float getWeight() {
    return weight;
  }
  
  public void setWeight(float weight) {
    this.weight = weight;
  }
  
  public int getPointsPerGame() {
    return pointsPerGame;
  }

  public void setPointsPerGame(int pointsPerGame) {
    this.pointsPerGame = pointsPerGame;
  }

  public int getAssistsPerGame() {
    return assistsPerGame;
  } 

  public void setAssistsPerGame(int assistsPerGame) {
    this.assistsPerGame = assistsPerGame;
  }

  public int getReboundsPerGame() {
    return reboundsPerGame;
  }

  public void setReboundsPerGame(int reboundsPerGame) {
    this.reboundsPerGame = reboundsPerGame;
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }
  
  public boolean isRookie() {
    return rookie;
  }
  
  public void setRookie(boolean rookie) {
    this.rookie = rookie;
  }

  public List<BasketBallPlayer> getTeamMates() {
    return teamMates;
  }

  public List<int[]> getPoints() {
    return points;
  }

  @Override public String toString() {
    return format("%s[%s %s, team=%s]", getClass().getSimpleName(), name.getFirst(), name.getLast(), team);
  }
}
