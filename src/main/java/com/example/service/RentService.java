package com.example.service;

import com.example.exception.CommonExceptionTypes;
import com.example.persistence.entity.BookEntity;
import com.example.persistence.entity.RentHistoryEntity;
import com.example.persistence.mapper.RentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RentService {

    private final RentMapper rentMapper;

    // 책 대여 처리
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void rent(Integer userId, Long bookId) {
        var book = rentMapper.findAvailableBook(bookId);
        System.out.println("book : " + book);
        if(book == null) throw CommonExceptionTypes.RESOURCE_NOT_FOUND.toException();
        // TODO price 관련 유저 잔액 차감 로직
//        rentMapper.rent(userId, bookId);
        // 대여했으니 대여 불가능 상태로 변경
        rentMapper.changeRentalStatus(bookId, false);
        // 대여가 완료됐으니 대여 이력 생성
        var history = RentHistoryEntity.from(book, userId);
        rentMapper.createHistory(history);

        // TODO 15~20 초 후 자동 반납 처리
        asyncReturnBookAfterRental(bookId);
    }

    private void asyncReturnBookAfterRental(Long bookId) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Integer delayTime = ThreadLocalRandom.current().nextInt(15, 20);
        System.out.println("delayTime : " +delayTime);
        executor.schedule(() -> {
            // 일정 시간이 지난 후 실행될 코드
            // 예시: Mapper의 특정 메소드 호출
            rentMapper.changeRentalStatus(bookId, true);
            System.out.println("updated");
        }, delayTime, TimeUnit.SECONDS); // 15~20초 후에 실행
         executor.shutdown(); // 작업이 끝난 후에는 executor를 종료해주어야 합니다.
    }

    public List<BookEntity> findBooksBy() {

        return null;
    }

}
