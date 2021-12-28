package app.controller;

import app.service.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/statistics")
public class ScoreboardController {

    private ScoreboardService scoreboardService;

    @Autowired
    public void setScoreboardService(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> changesToScoreboard() {
        List<String> listToScoreboard = scoreboardService.getListToScoreboard();
        return ResponseEntity.ok(listToScoreboard);
    }

}
