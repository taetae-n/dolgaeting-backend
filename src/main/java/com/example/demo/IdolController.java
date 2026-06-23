package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class IdolController {

    private final IdolRepository idolRepository;
    private final TagRepository tagRepository;
    private final IdolTagRepository idolTagRepository;

    public IdolController(IdolRepository idolRepository, TagRepository tagRepository, IdolTagRepository idolTagRepository) {
        this.idolRepository = idolRepository;
        this.tagRepository = tagRepository;
        this.idolTagRepository = idolTagRepository;
    }

    @GetMapping("/api/idols")
    public List<Idol> getAllIdols() {
        return idolRepository.findAll();
    }

    @PostMapping("/api/idols")
    public Idol createIdol(@RequestBody Idol idol) {
        return idolRepository.save(idol);
    }

    @GetMapping("/api/idols/gender/{gender}")
    public List<Idol> getIdolsByGender(@PathVariable String gender) {
        return idolRepository.findByGender(gender);
    }

    @PostMapping("/api/idols/{idolId}/tags/{tagId}")
    public IdolTag addTagToIdol(@PathVariable Long idolId, @PathVariable Long tagId) {
        Idol idol = idolRepository.findById(idolId).orElseThrow();
        Tag tag = tagRepository.findById(tagId).orElseThrow();

        IdolTag idolTag = new IdolTag();
        idolTag.setIdol(idol);
        idolTag.setTag(tag);

        return idolTagRepository.save(idolTag);
    }

    @DeleteMapping("/api/idols/{idolId}/tags/{tagId}")
    public void removeTagFromIdol(@PathVariable Long idolId, @PathVariable Long tagId) {
        List<IdolTag> links = idolTagRepository.findByIdolId(idolId);
        for (IdolTag link : links) {
            if (link.getTag().getId().equals(tagId)) {
                idolTagRepository.delete(link);
            }
        }
    }

    @DeleteMapping("/api/idols/{idolId}")
    public void deleteIdol(@PathVariable Long idolId) {
        List<IdolTag> links = idolTagRepository.findByIdolId(idolId);
        idolTagRepository.deleteAll(links);
        idolRepository.deleteById(idolId);
    }

    @PutMapping("/api/idols/{idolId}")
    public Idol updateIdol(@PathVariable Long idolId, @RequestBody Idol updated) {
        Idol idol = idolRepository.findById(idolId).orElseThrow();
        idol.setName(updated.getName());
        idol.setGroupName(updated.getGroupName());
        idol.setGender(updated.getGender());
        idol.setMbti(updated.getMbti());
        idol.setFancamUrl(updated.getFancamUrl());
        idol.setPhotoUrl(updated.getPhotoUrl());
        idol.setIntroPoint(updated.getIntroPoint());
        return idolRepository.save(idol);
    }

    @GetMapping("/api/idols/{idolId}/tags")
    public List<Tag> getIdolTags(@PathVariable Long idolId) {
        List<IdolTag> links = idolTagRepository.findByIdolId(idolId);
        List<Tag> result = new ArrayList<>();
        for (IdolTag link : links) {
            result.add(link.getTag());
        }
        return result;
    }
}