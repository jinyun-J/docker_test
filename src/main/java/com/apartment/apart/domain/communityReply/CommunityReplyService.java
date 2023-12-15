package com.apartment.apart.domain.communityReply;

import com.apartment.apart.DataNotException;
import com.apartment.apart.domain.community.Community;
import com.apartment.apart.domain.user.SiteUser;
import org.springframework.stereotype.Service;


@Service
public class CommunityReplyService {
    private final CommunityReplyRepository communityReplyRepository;

    public CommunityReplyService(CommunityReplyRepository communityReplyRepository) {
        this.communityReplyRepository = communityReplyRepository;
    }

    public CommunityReply create(Community community, String content, SiteUser nickname) {
        CommunityReply communityReply = new CommunityReply(nickname, community, content);
        this.communityReplyRepository.save(communityReply);

        return communityReply;
    }

    public CommunityReply getCommunityReply(Integer id) {
        return this.communityReplyRepository.findById(id)
                .orElseThrow(() -> new DataNotException("답변을 찾을 수 없습니다."));
    }

    public void modify(CommunityReply communityReply, String content) {
        CommunityReply modifiedCommunityReply = communityReply.modify(content);
        this.communityReplyRepository.save(modifiedCommunityReply);
    }
    public void delete(CommunityReply communityReply) {
        this.communityReplyRepository.delete(communityReply);
    }

}
