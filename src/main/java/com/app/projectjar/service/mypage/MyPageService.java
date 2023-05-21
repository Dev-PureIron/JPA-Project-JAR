package com.app.projectjar.service.mypage;

import com.app.projectjar.domain.calendar.CalendarDTO;
import com.app.projectjar.domain.diary.DiaryDTO;
import com.app.projectjar.domain.file.FileDTO;
import com.app.projectjar.domain.member.MemberDTO;
import com.app.projectjar.entity.diary.Diary;
import com.app.projectjar.entity.file.diary.DiaryFile;
import com.app.projectjar.entity.file.suggest.SuggestFile;
import com.app.projectjar.entity.member.Member;
import com.app.projectjar.entity.suggest.Suggest;

import java.util.ArrayList;
import java.util.List;

public interface MyPageService {

    public void registerDiary(DiaryDTO diaryDTO,Long memberId);

    // 현재 시퀀스 가져오기
    public Diary getCurrentSequence();

    List<CalendarDTO> getCalendarDTO(Long memberId);

    public DiaryDTO getDiary(Long diaryId);



    default DiaryDTO toDiaryDTO(Diary diary) {
        return DiaryDTO.builder()
                .memberDTO(toMemberDTO(diary.getMember()))
                .diaryStatus(diary.getDiaryStatus())
                .boardTitle(diary.getBoardTitle())
                .boardContent(diary.getBoardContent())
                .fileDTOS(fileToDTO(diary.getDiaryFiles()))
                .id(diary.getId())
                .start(diary.getCreatedDate())
                .end(diary.getCreatedDate())
                .build();
    }

    default MemberDTO toMemberDTO(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .memberEmail(member.getMemberEmail())
                .memberPassword(member.getMemberPassword())
                .memberName(member.getMemberName())
                .memberNickname(member.getMemberNickname())
                .memberPhoneNumber(member.getMemberPhoneNumber())
                .memberStatus(member.getMemberStatus())
                .build();
    }

    default List<FileDTO> fileToDTO(List<DiaryFile> diaryFiles) {
        List<FileDTO> diaryFileDTOList = new ArrayList<>();
        diaryFiles.forEach(
                diaryFile -> {
                    FileDTO fileDTO = FileDTO.builder()
                            .id(diaryFile.getId())
                            .fileOriginalName(diaryFile.getFileOriginalName())
                            .fileUuid(diaryFile.getFileUuid())
                            .filePath(diaryFile.getFilePath())
                            .fileType(diaryFile.getFileType())
                            .build();
                    diaryFileDTOList.add(fileDTO);
                }
        );
        return diaryFileDTOList;
    }

    default Diary toDiaryEntity(DiaryDTO diaryDTO){
        return Diary.builder()
                .boardContent(diaryDTO.getBoardContent())
                .boardTitle(diaryDTO.getBoardTitle())
                .diaryStatus(diaryDTO.getDiaryStatus())
                .member(toMemberEntity(diaryDTO.getMemberDTO()))
                .build();
    }

    default Member toMemberEntity(MemberDTO memberDTO) {
        return Member.builder()
                .id(memberDTO.getId())
                .memberEmail(memberDTO.getMemberEmail())
                .memberName(memberDTO.getMemberName())
                .memberNickname(memberDTO.getMemberNickname())
                .memberPassword(memberDTO.getMemberPassword())
                .memberPhoneNumber(memberDTO.getMemberPhoneNumber())
                .memberStatus(memberDTO.getMemberStatus())
                .badgeType(memberDTO.getBadgeType())
                .build();
    }

    default DiaryFile toDiaryFileEntity(FileDTO fileDTO){
        return DiaryFile.builder()
                .id(fileDTO.getId())
                .fileOriginalName(fileDTO.getFileOriginalName())
                .fileUuid(fileDTO.getFileUuid())
                .filePath(fileDTO.getFilePath())
                .diary(fileDTO.getDiary())
                .fileType(fileDTO.getFileType())
                .build();
    }

}
