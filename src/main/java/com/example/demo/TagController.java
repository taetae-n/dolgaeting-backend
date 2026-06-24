package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("/api/tags")
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @PostMapping("/api/tags")
    public Tag createTag(@RequestBody Tag tag) {
        return tagRepository.save(tag);
    }
}