package com.app.projectjar.service.groupChallenge;

import com.app.projectjar.domain.file.FileDTO;
import com.app.projectjar.domain.groupChallenge.GroupChallengeDTO;
import com.app.projectjar.entity.groupChallenge.GroupChallenge;
import com.app.projectjar.repository.file.groupChallenge.GroupChallengeFileRepository;
import com.app.projectjar.repository.groupChallenge.GroupChallengeRepository;
import com.app.projectjar.type.FileType;
import com.app.projectjar.type.GroupChallengeType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("groupChallenge") @Primary
public class GroupChallengeServiceImpl implements GroupChallengeService {
    private final GroupChallengeRepository groupChallengeRepository;
    private final GroupChallengeFileRepository groupChallengeFileRepository;

    @Override
    public Page<GroupChallengeDTO> getGroupChallengeList(Pageable pageable) {
        Page<GroupChallenge> groupChallenges = groupChallengeRepository.findAllGroupChallengeWithPaging_QueryDsl(pageable);
        List<GroupChallengeDTO> groupChallengeDTOS = groupChallenges.stream().map(this::toGroupChallengeDTO).collect(Collectors.toList());
        return new PageImpl<>(groupChallengeDTOS, groupChallenges.getPageable() , groupChallenges.getTotalElements());
    }

    @Override
    public Page<GroupChallengeDTO> getAllGroupChallengesWithPaging(int page) {
        Page<GroupChallenge> groupChallenges = groupChallengeRepository.findAllWithPaging_QueryDSL(PageRequest.of(page, 10));
        List<GroupChallengeDTO> groupChallengeDTOS = groupChallenges.getContent().stream()
                .map(this::toGroupChallengeDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(groupChallengeDTOS, groupChallenges.getPageable(), groupChallenges.getTotalElements());
    }

    @Override
    public Page<GroupChallengeDTO> getEndGroupChallengeList(Pageable pageable) {
        Page<GroupChallenge> groupChallenges = groupChallengeRepository.findAllGroupChallengeByPrivateWithPaging_QueryDsl(pageable);
        List<GroupChallengeDTO> groupChallengeDTOS = groupChallenges.stream().map(this::toGroupChallengeDTO).collect(Collectors.toList());

        return new PageImpl<>(groupChallengeDTOS, groupChallenges.getPageable() , groupChallenges.getTotalElements());
    }

    @Override
    public GroupChallengeDTO getGroupChallenge(Long groupChallengeId) {
        GroupChallengeDTO groupChallengeDTO = groupChallengeRepository.findByGroupChallengeId_QueryDsl(groupChallengeId).map(this::toGroupChallengeDTO).get();
        return groupChallengeDTO;
    }

    @Override
    public GroupChallenge getCurrentSequence() {
        return groupChallengeRepository.getCurrentSequence_QueryDsl();
    }

    @Override
    public List<GroupChallenge> getListByEndDate(LocalDate endDate) {
        return groupChallengeRepository.findByEndDate(endDate);
    }

    @Override
    public List<GroupChallenge> getListByStartDate(LocalDate startDate) {
        return groupChallengeRepository.findByStartDate(startDate);
    }

    @Override
    public void updateGroupChallengeTypeToOpen(List<GroupChallenge> groupChallengeList) {
        groupChallengeList.stream().forEach(
                groupChallenge -> {
                    groupChallenge.setGroupChallengeStatus(GroupChallengeType.OPEN);
                    groupChallengeRepository.save(groupChallenge);
                }
        );
    }

    @Override
    public void updateGroupChallengeTypeToPrivate(List<GroupChallenge> groupChallengeList) {
        groupChallengeList.stream().forEach(
                groupChallenge -> {
                    groupChallenge.setGroupChallengeStatus(GroupChallengeType.PRIVATE);
                    groupChallengeRepository.save(groupChallenge);
                }
        );
    }


    @Override
    public void deleteGroupChallenges(List<Long> groupChallengeIds) {
        for (Long groupChallengeId : groupChallengeIds) {
            groupChallengeRepository.deleteById(groupChallengeId);
        }
    }

    // 그룹 챌린지 게시판 등록
    @Override @Transactional
    public void register(GroupChallengeDTO groupChallengeDTO) {
        List<FileDTO> fileDTOS = groupChallengeDTO.getFileDTOS();


        groupChallengeRepository.save(toGroupChallengeEntity(groupChallengeDTO));
        if(fileDTOS != null){
            for (int i = 0; i < fileDTOS.size(); i++) {
                if(i == 0){
                    fileDTOS.get(i).setFileType(FileType.REPRESENTATIVE);
                }else {
                    fileDTOS.get(i).setFileType(FileType.NORMAL);
                }
                fileDTOS.get(i).setGroupChallenge(getCurrentSequence());
                groupChallengeFileRepository.save(toGroupChallengeFileEntity(fileDTOS.get(i)));
            }
        }
    }
}
