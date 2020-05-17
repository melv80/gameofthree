package com.melv.gameofthree.main.config;

import com.melv.gameofthree.main.model.Player;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import java.util.Random;


/**
 * Service configuration.
 */
@Configuration
@ConfigurationProperties(prefix = "game")
public class ServiceConfig {

  @NotNull
  private String playerName = "unknown";

  private String remote;

  @NotNull
  private String local;

  private int emitterDelay =500;

  private long startingNumber = -1;

  private int playerID = new Random().nextInt(Integer.MAX_VALUE-2)+1;


  public Player createLocalPlayer() {
    return new Player(playerName, playerID);
  }

  /**
   * endpoint of this service. Used to receive incoming network requests.
   * A fully qualified host adress like http://localhost:90/api/v1
   */
  public @NotNull String getLocal() {
    return local;
  }

  public void setLocal(String local) {
    this.local = local;
  }

  /**
   * endpoint that should be connected to upon startup.
   * A fully qualified host adress like http://localhost:90/api/v1
   */
  public String getRemote() {
    return remote;
  }

  public void setRemote(String remote) {
    this.remote = remote;
  }

  /**
   * @return name of the local player or "unknown" if not set
   */
  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  /**
   * @return id of the local player, generated by local instance if not set
   */
  public int getPlayerID() {
    return playerID;
  }

  public void setPlayerID(int playerID) {
    this.playerID = playerID;
  }

  /**
   * delay in ms for mitting web UI events
   */
  public int getEmitterDelay() {
    return emitterDelay;
  }

  public void setEmitterDelay(int emitterDelay) {
    this.emitterDelay = emitterDelay;
  }

  /**
   * @return a starting number <0 means the computer should generate one
   */
  public long getStartingNumber() {
    return startingNumber;
  }

  public void setStartingNumber(long startingNumber) {
    this.startingNumber = startingNumber;
  }
}
