package com.arbriver.tributaryutils;

import java.io.IOException;
import java.time.Instant;

import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@RequestScope
public abstract class AbstractMatch {

    private final static ObjectMapper oMapper = new ObjectMapper();
    static {
        oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        oMapper.registerModule(new JavaTimeModule());
    }

    @Id
    @Column(name = "event_id")
    private String eventId;

    @Column
    private String sport = "TENNIS";

    @Column
    private String book = "FANDUEL";

    @Column(name = "side_a_name")
    private String sideAName;

    @Column(name = "side_b_name")
    private String sideBName;

    @Column
    private Instant modifiedAt;

    @Override
    public int hashCode() {
        return (this.getSideAName() + this.getSideBName() + "tennis").hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String toShortString() {
        return String.format("EventID: %s Timestamp: %s",
                this.getEventId(), this.getModifiedAt().toString());
    }

    public byte[] toJsonAsBytes() {
        try {
            return oMapper.writeValueAsBytes(this);
        } catch (IOException e) {
            return null;
        }
    }
}
