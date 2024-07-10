package com.nisum.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phone")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    private String cityCode;
    private String countryCode;

}
