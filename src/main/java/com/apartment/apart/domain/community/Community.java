package com.apartment.apart.domain.community;

import com.apartment.apart.domain.communityReply.CommunityReply;
import com.apartment.apart.domain.user.SiteUser;
import com.apartment.apart.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Community extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser user; //작성자 정보
    @Column(length = 50)
    private String title; //게시글 제목
    @Column(columnDefinition = "TEXT")
    private String content;//게시글 내용

    @ManyToMany
    Set<SiteUser> likeCount;//종아요 수

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<CommunityReply> replyList = new ArrayList<>();
}
