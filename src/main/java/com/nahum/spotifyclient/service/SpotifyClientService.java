package com.nahum.spotifyclient.service;

import java.io.IOException;
import javax.annotation.PostConstruct;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyApi.Builder;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
public class SpotifyClientService {
    private String clientId;
    private String clientSecret;
    private SpotifyApi spotifyApi;

    @PostConstruct
    private final void postConstructInit() {
        this.spotifyApi = (new Builder()).setClientId(this.clientId).setClientSecret(this.clientSecret).build();
    }

    private final void clientCredentialsSync() {
        try {
            ClientCredentialsRequest clientCredentialsRequest;
            if (this.spotifyApi != null) {
                clientCredentialsRequest = this.spotifyApi.clientCredentials().build();
                var clientCredentials = clientCredentialsRequest.execute();
                this.spotifyApi.setAccessToken(clientCredentials != null ? clientCredentials.getAccessToken() : null);
                System.out.println("Expires in: " + (clientCredentials != null ? clientCredentials.getExpiresIn() : null));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public Track searchTracksSync(String q) {
        this.clientCredentialsSync();

        try {
            SearchTracksRequest searchTracksRequest = this.spotifyApi.searchTracks("isrc:" + q).build();
            Paging trackPaging = searchTracksRequest != null ? searchTracksRequest.execute() : null;
            if (trackPaging != null && trackPaging.getTotal() > 0) {
                System.out.println("Total: " + trackPaging.getTotal());
                return ((Track[])trackPaging.getItems())[0];
            }
            return null;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    @Autowired
    public SpotifyClientService(
            @Value("${spotify.client-id}") String pClientId,
            @Value("${spotify.client-secret}") String pClientSecret
    ) {
        super();
        this.clientId = pClientId;
        this.clientSecret = pClientSecret;
    }
}
