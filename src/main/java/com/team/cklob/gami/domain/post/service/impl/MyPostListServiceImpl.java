package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.post.dto.response.PostListResponse;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.MyPostListService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPostListServiceImpl implements MyPostListService {

    private final PostRepository postRepository;
    private final MemberUtil memberUtil;

    @Override
    public List<PostListResponse> getMyPosts (){
        Member member = memberUtil.getCurrentMember();
        return postRepository.findAllByMemberId(member.getId())
                .stream()
                .map(PostListResponse::from)
                .toList();
    }
}
