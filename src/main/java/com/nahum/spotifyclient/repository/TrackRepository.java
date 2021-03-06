package com.nahum.spotifyclient.repository;

import com.nahum.spotifyclient.model.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends CrudRepository<Track, Long> {
    Track findByIsrc(String isrc);
}
