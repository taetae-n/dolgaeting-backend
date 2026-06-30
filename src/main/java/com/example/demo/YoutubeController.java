package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class YoutubeController {

    @Value("${youtube.api.key}")
    private String apiKey;

    @GetMapping("/api/youtube/search")
    public List<Map<String, String>> searchVideos(@RequestParam String query) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://www.googleapis.com/youtube/v3/search"
                + "?part=snippet"
                + "&maxResults=3"
                + "&q=" + query
                + "&type=video"
                + "&key=" + apiKey;

        Map response = restTemplate.getForObject(url, Map.class);

        List<Map<String, String>> results = new ArrayList<>();
        List<Map> items = (List<Map>) response.get("items");

        for (Map item : items) {
            Map id = (Map) item.get("id");
            Map snippet = (Map) item.get("snippet");
            Map thumbnails = (Map) snippet.get("thumbnails");
            Map medium = (Map) thumbnails.get("medium");

            Map<String, String> video = new HashMap<>();
            video.put("videoId", (String) id.get("videoId"));
            video.put("title", (String) snippet.get("title"));
            video.put("thumbnail", (String) medium.get("url"));
            results.add(video);
        }

        return results;
    }
}