package com.nahum.spotifyclient.rest;

import com.nahum.spotifyclient.model.Track;
import com.nahum.spotifyclient.repository.TrackRepository;
import com.nahum.spotifyclient.service.SpotifyClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CodingChallengeController {

    private final TrackRepository trackRepository;

    private final SpotifyClientService spotifyClientService;

    @Autowired
    public CodingChallengeController(TrackRepository trackRepository, SpotifyClientService spotifyClientService) {
        this.trackRepository = trackRepository;
        this.spotifyClientService = spotifyClientService;
    }

    public TrackRepository getTrackRepository() {
        return this.trackRepository;
    }

    public SpotifyClientService getSpotifyClientService() {
        return this.spotifyClientService;
    }

    @GetMapping({"/tracks"})
    public Iterable getAllTracks() {
        return this.getTrackRepository().findAll();
    }

    @GetMapping({"/codingchallenge/getTrack"})
    public Track getTrack(@RequestParam("isrc") String isrc) {
        Track localTrack = this.getTrackRepository().findByIsrc(isrc);
        if (localTrack != null) {
            return localTrack;
        }

        throw new ResponseStatusException(404, "Track not found.", null);
    }

    @PostMapping({"/codingchallenge/createTrack"})
    public Track createTrack(@RequestParam("isrc") String isrc) {
        Track localTrack = this.getTrackRepository().findByIsrc(isrc);
        if (localTrack != null) {
            throw new ResponseStatusException(409, "Record exists!", null);
        } else {
            SpotifyClientService var5 = this.getSpotifyClientService();
            if (var5 != null) {
                se.michaelthelin.spotify.model_objects.specification.Track remoteSpotifyTrack = var5.searchTracksSync(isrc);
                if (remoteSpotifyTrack != null) {
                    Track result = new Track(
                            isrc,
                            remoteSpotifyTrack.getName(),
                            remoteSpotifyTrack.getDurationMs(),
                            remoteSpotifyTrack.getIsExplicit()
                    );
                    return (Track)this.getTrackRepository().save(result);
                }
            }
            throw new ResponseStatusException(404, "Remote record not found.", null);
        }
    }
}
