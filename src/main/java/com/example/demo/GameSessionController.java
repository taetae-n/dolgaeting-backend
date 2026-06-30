package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class GameSessionController {

    private final GameSessionRepository gameSessionRepository;

    public GameSessionController(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    @PostMapping("/api/sessions")
    public GameSession createSession(@RequestBody GameSession session) {
        session.setCreatedAt(LocalDateTime.now());
        return gameSessionRepository.save(session);
    }

    @GetMapping("/api/sessions")
    public List<GameSession> getAllSessions() {
        return gameSessionRepository.findAll();
    }

    @DeleteMapping("/api/sessions/{id}")
    public void deleteSession(@PathVariable Long id) {
        gameSessionRepository.deleteById(id);
    }

    @DeleteMapping("/api/sessions")
    public void deleteAllSessions() {
        gameSessionRepository.deleteAll();
    }
}
