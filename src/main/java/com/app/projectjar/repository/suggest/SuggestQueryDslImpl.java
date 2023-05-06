package com.app.projectjar.repository.suggest;


import com.app.projectjar.domain.dto.BoardDTO;
import com.app.projectjar.domain.dto.FileDTO;
import com.app.projectjar.domain.dto.QBoardDTO;
import com.app.projectjar.domain.dto.QFileDTO;
import com.app.projectjar.entity.file.suggest.QSuggestFile;
import com.app.projectjar.entity.file.suggest.SuggestFile;
import com.app.projectjar.entity.suggest.QSuggest;
import com.app.projectjar.entity.suggest.Suggest;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.io.File;
import java.util.List;

import static com.app.projectjar.entity.file.suggest.QSuggestFile.suggestFile;
import static com.app.projectjar.entity.suggest.QSuggest.suggest;


@RequiredArgsConstructor
public class SuggestQueryDslImpl implements SuggestQueryDsl {
    private final JPAQueryFactory query;
    @Override
    public Page<BoardDTO> findAllWithPaging(Pageable pageable) {
        List<BoardDTO> foundSuggest = query.select(
                new QBoardDTO(
                        suggest.id,
                        suggest.boardTitle,
                        suggest.boardContent,
                        suggest.boardType
                ))
                .from(suggest)
                .orderBy(suggest.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        foundSuggest.stream().forEach(
                boardDTO -> {
                    List<FileDTO> fileDTOS = query.select(new QFileDTO(
                            suggestFile.id,
                            suggestFile.fileOriginalName,
                            suggestFile.fileUuid,
                            suggestFile.filePath
                    )).from(suggestFile)
                            .where(suggestFile.suggest.id.eq(boardDTO.getBoardId()))
                            .fetch();
                boardDTO.setFiles(fileDTOS);
                }
        );

        Long count = query.select(suggest.count())
                .from(suggest)
                .fetchOne();

        return new PageImpl<>(foundSuggest, pageable, count);
    }
}
