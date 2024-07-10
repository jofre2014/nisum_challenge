package com.nisum.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@MappedSuperclass
@ToString
@Setter
@Getter
public abstract class BaseEntity {
    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @PrePersist
    void onCreate() {
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }

}
