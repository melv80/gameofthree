package com.melv.gameofthree.main.controller;

import com.melv.gameofthree.main.api.game.GameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * web inter face controls
 */
@Controller
public class WebController {

    @Autowired
    private GameManager gameManager;

    @GetMapping("/")
    public String index(final Model model) {
      model.addAttribute("player", gameManager.localPlayerName());
      model.addAttribute("opponent", gameManager.opponentName());
        return "index";
    }

    @PostMapping("/newgame")
    public String newgame() {
        gameManager.startNewGame();
        return "index";
    }

}
