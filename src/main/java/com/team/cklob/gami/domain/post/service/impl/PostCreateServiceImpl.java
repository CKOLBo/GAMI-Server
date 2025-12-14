package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberException;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.domain.post.dto.request.PostCreateRequest;
import com.team.cklob.gami.domain.post.dto.request.PostImageRequest;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.entity.PostImage;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.PostCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCreateServiceImpl implements PostCreateService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long create(PostCreateRequest request, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        Post post = Post.create(member, request.getTitle(), request.getContent());
        postRepository.save(post);

        if (request.getImages() != null) {
            for (PostImageRequest img : request.getImages()) {
                PostImage postImage = PostImage.builder()
                        .post(post)
                        .imageUrl(img.getImageUrl())
                        .sequence(img.getSequence())
                        .build();

                post.getImages().add(postImage);
            }
        }

        return post.getId();
    }
}
