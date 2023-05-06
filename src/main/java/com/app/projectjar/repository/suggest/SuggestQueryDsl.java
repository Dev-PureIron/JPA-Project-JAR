package com.app.projectjar.repository.suggest;

import com.app.projectjar.domain.dto.BoardDTO;
import com.app.projectjar.entity.suggest.Suggest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SuggestQueryDsl {
    // 전체 조회
    public Page<BoardDTO> findAllWithPaging(Pageable pageable);
}
