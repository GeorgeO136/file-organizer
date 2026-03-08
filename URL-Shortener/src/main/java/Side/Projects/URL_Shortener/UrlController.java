package Side.Projects.URL_Shortener;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UrlController {
    private Map<String, String> urlDataBase = new HashMap<>();

    @PostMapping("/shorten")
    public UrlMapping shortenUrl(@RequestBody Map<String , String> request){
        String originalUrl = request.get("url");

        String shortCode = UUID.randomUUID()
                .toString()
                .substring(0, 6);

        urlDataBase.put(shortCode, originalUrl);

        return new UrlMapping(originalUrl, shortCode);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode){
        String originalUrl = urlDataBase.get(shortCode);

        if(originalUrl == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .status(302)
                .location(URI.create(originalUrl))
                .build();
    }

}
