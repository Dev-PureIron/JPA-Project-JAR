package com.app.projectjar.service.suggest;

import com.app.projectjar.domain.file.FileDTO;
import com.app.projectjar.domain.member.MemberDTO;
import com.app.projectjar.domain.suggest.SuggestDTO;
import com.app.projectjar.entity.suggest.Suggest;
import com.app.projectjar.entity.suggest.SuggestLike;
import com.app.projectjar.repository.file.suggest.SuggestFileRepository;
import com.app.projectjar.repository.member.MemberRepository;
import com.app.projectjar.repository.suggest.SuggestLikeRepository;
import com.app.projectjar.repository.suggest.SuggestReplyRepository;
import com.app.projectjar.repository.suggest.SuggestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("suggest") @Primary
@Slf4j
public class SuggestServiceImpl implements SuggestService {
    private final SuggestRepository suggestRepository;

    private final SuggestFileRepository suggestFileRepository;

    private final MemberRepository memberRepository;

    private final SuggestLikeRepository suggestLikeRepository;

    private final SuggestReplyRepository suggestReplyRepository;


    @Override
    public void register(SuggestDTO suggestDTO, Long memberId) {
        List<FileDTO> fileDTOS = suggestDTO.getFileDTOS();

        memberRepository.findById(memberId).ifPresent(
                member -> suggestDTO.setMemberDTO(toMemberDTO(member))
        );

        suggestRepository.save(toSuggestEntity(suggestDTO));
        log.info(fileDTOS.size()+"11111111111111111");
        if(!fileDTOS.isEmpty()){
            fileDTOS.forEach(
                    fileDTO -> {
                        fileDTO.setSuggest(getCurrentSequence());
                        suggestFileRepository.save(toSuggestFileEntity(fileDTO));
                    }
            );
        }
    }

    @Override
    public List<SuggestDTO> getSuggestList(Pageable pageable) {
        List<SuggestDTO> suggestDTOS = new ArrayList<>();
        suggestRepository.findAllWithPaging_QueryDsl(pageable).forEach(
                suggest -> {
                    suggestDTOS.add(toSuggestDTO(suggest));
                }
        );
        return suggestDTOS;
    }

    @Override
    public SuggestDTO getSuggest(Long suggestId) {
        Optional<Suggest> suggest = suggestRepository.findByIdSuggest_QueryDsl(suggestId);
        return toSuggestDTO(suggest.get());
    }

    @Override
    public Suggest getCurrentSequence() {
        return suggestRepository.getCurrentSequence();
    }

}