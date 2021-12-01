package com.nahum.spotifyclient.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public final class Track {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            columnDefinition = "serial"
    )
    private Long id;
    @Column(nullable = false)
    private String isrc;
    @Column(nullable = false)
    private String name;
    private int duration;
    private boolean explicit;

    public final Long getId() {
        return this.id;
    }

    public final void setId(Long var1) {
        this.id = var1;
    }

    public final String getIsrc() {
        return this.isrc;
    }

    public final void setIsrc(String var1) {
        this.isrc = var1;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String var1) {
        this.name = var1;
    }

    public final int getDuration() {
        return this.duration;
    }

    public final void setDuration(int var1) {
        this.duration = var1;
    }

    public final boolean getExplicit() {
        return this.explicit;
    }

    public final void setExplicit(boolean var1) {
        this.explicit = var1;
    }

    public Track(Long id, String isrc, String name, int duration, boolean explicit) {
        super();
        this.id = id;
        this.isrc = isrc;
        this.name = name;
        this.duration = duration;
        this.explicit = explicit;
    }

    public Track(String isrc, String name, int duration, boolean explicit) {
        this((Long)null, isrc, name, duration, explicit);
    }

    public Track() {
    }
}
