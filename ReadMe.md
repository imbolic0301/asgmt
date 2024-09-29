# 마크다운 용

## 개념 정의

1. 회원
    1. 속성
        1. 고유 번호
        2. 사용자명
        3. 로그인 아이디
            1. 중복 금지
        4. 비밀번호
            1. DB 암호화 필수
2. 챌린지
    1. 고유 챌린지 번호
    2. 이름
    3. 최소 보증금
    4. 최대 보증금
    5. 챌린지 상태
    6. 시작일
    7. 공부 시간


## 기능 정의

1. 회원
    1. 로그인
        1. 아이디, 비밀번호 입력
            1. 잘못 입력시 Exception 발생
    2. 내 정보 조회
        1. 토큰 검증
            1. 검증 실패시 Exception 발생
2. 챌린지
    1. 등록
    2. 결제
    3. 조회

## 구현 TO-DO

1. 최소 기능 구현
2. 글로벌 적용 기능
    1. 토큰 검증
3.