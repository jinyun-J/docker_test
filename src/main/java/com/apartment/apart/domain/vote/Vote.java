package com.apartment.apart.domain.vote;


import com.apartment.apart.domain.user.SiteUser;
import com.apartment.apart.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Vote extends BaseEntity {
    @JoinColumn(name = "user_id")
    private SiteUser user;

    private String title;

    private String content;

    Set<SiteUser> agree;

    Set<SiteUser> disagree;

    private LocalDate startDate;

    private LocalDate endDate;
}