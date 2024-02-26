package com.example.service;

import com.example.persistence.entity.BookEntity;
import com.example.persistence.entity.ConsignmentHistoryEntity;
import com.example.persistence.mapper.ConsignmentMapper;
import com.example.web.dto.ConsignmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsignmentService {

    private final ConsignmentMapper consignmentMapper;


    // 책 위탁하기(등록하기)
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void consignBook(ConsignmentDto.Request request) {
        var newBook = BookEntity.fromValidated(request);
        consignmentMapper.createNewBook(newBook);
        var history = ConsignmentHistoryEntity.from(newBook);
        consignmentMapper.createHistory(history);
    }

}
