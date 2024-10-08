# 실행 환경 및 방법
1. 실행 환경
   1. JDK : 17
   2. IDE : IntelliJ IDEA PRO
   3. mysql 또는 mariadb 설치
      1. 설치 완료시, 하단의 SQL 실행해서 초기 테이블 및 데이터 삽입 ('구동 및 테스트를 위한 테이블 생성 및 데이터 삽입 쿼리' 참조)
2. 실행 방법
   1. IntelliJ IDEA PRO 로 프로젝트를 연 다음, Spring Boot Application 구동을 합니다.
   2. 프로젝트 구동이 완료되면 'http://localhost:8080/swagger-ui/index.html' 으로 접속해 테스트합니다.

## IDE 세팅
1. 사용 IDE : IntelliJ IDEA Ultimate
   1. gradle 의 jdk 버전을 17로 설정
   2. Optimize imports on the fly 옵션 활성화

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

1. API 최소 기능 구현
2. 모듈
   1. 토큰 관리 모듈
      1. 관리 : RDB
      2. filter 대신 ArgumentResolver 사용 (파라미터 바인딩으로 개발 편의 고려)
      3. JWT 라이브러리 적용 (JJWT)
   2. 암호화
      1. SHA256 (단방향 암호화, 패스워드에 적용)
   3. 공용 응답 적용
   4. 스웨거

## 고도화 고려
1. 테스트 코드 작성 필요 (entity 나 dto 의 도메인 로직이나 검증 위주로)
2. JPA 적용 (entity 로 테이블 생성 자동화 처리 등 필요)
3. gradle 테스트 과정에서 테스트 환경에 H2 인메모리로 쿼리 기능 실행 후 테스트
4. 결제 이력과 챌린지 이력 조회는 2depth 의 리스트 조회 - 결제 이력의 ID 로 챌린지 이력을 WHERE 절에 IN 으로 조회한 다음, 결제 이력의 ID 에 매핑하는 방식으로 성능 개선

### 구동 및 테스트를 위한 테이블 생성 및 데이터 삽입 쿼리
JPA 를 사용하더라도, 
실제 인덱스 등을 직접 관리하는 게 안전하기에 DDL 관련 옵션은 hibernate.hbm2ddl.auto 로 고정하고 
직접 테이블 생성 쿼리를 관리한다.

1. 유저 관련

```SQL
# 유저 메인 테이블
CREATE TABLE `user` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`login_id` varchar(32) NOT NULL,
`user_name` varchar(64) NOT NULL,
`password` varchar(255) NOT NULL,
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
`is_del` tinyint(1) NOT NULL DEFAULT '0',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='회원 테이블';

# 유저 잔고 테이블
CREATE TABLE `user_balance` (
                                `user_id` int(11) NOT NULL,
                                `balance` decimal(10,1) NOT NULL COMMENT '현재 잔고',
                                `deposit` decimal(10,1) NOT NULL COMMENT '현재 챌린지에 참여 중인 보증금',
                                `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `updated_at` datetime DEFAULT NULL,
                                PRIMARY KEY (`user_id`),
                                KEY `user_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='유저 현재 잔고 테이블';

# 유저 토큰 발급 테이블
CREATE TABLE `user_token_history` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `user_id` int(11) NOT NULL COMMENT 'user 테이블 PK',
                                      `access_token` char(36) NOT NULL,
                                      `access_token_expire_at` datetime DEFAULT NULL,
                                      `refresh_token` char(36) NOT NULL COMMENT '회원 세션 갱신용 토큰',
                                      `refresh_token_expire_at` datetime DEFAULT NULL,
                                      `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      `updated_at` datetime DEFAULT NULL,
                                      `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '토큰 활성화 상태 - 0 : 사용 가능 / 1 : 사용 불가능',
                                      PRIMARY KEY (`id`),
                                      KEY `user_idx` (`user_id`),
                                      KEY `ac_idx` (`access_token`),
                                      KEY `rc_idx` (`refresh_token`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='유저 토큰 발급 이력 테이블';

```

2. 챌린지 관련


```SQL
# 챌린지 테이블
CREATE TABLE `challenge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `deposit_min` decimal(10,1) NOT NULL COMMENT '최소 보증금',
  `deposit_max` decimal(10,1) NOT NULL COMMENT '최대 보증금',
  `status` varchar(16) NOT NULL DEFAULT 'DEACTIVE' COMMENT '챌린지의 상태\n활성화, 비활성화 / ACTVIE, DEACTIVE',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='챌린지를 관리하는 메인 테이블';

# 챌린지 과제 고정값 삽입용 쿼리
INSERT INTO challenge (`id`,`title`,`deposit_min`,`deposit_max`,`status`,`created_at`,`updated_at`) VALUES (1,'원하는 일정으로\n공부하기',10000.0,100000.0,'ACTIVE','2024-10-03 16:53:58',NULL);
INSERT INTO challenge (`id`,`title`,`deposit_min`,`deposit_max`,`status`,`created_at`,`updated_at`) VALUES (2,'월요일 1시간 공부\n하기',10000.0,50000.0,'ACTIVE','2024-10-03 16:53:58',NULL);
INSERT INTO challenge (`id`,`title`,`deposit_min`,`deposit_max`,`status`,`created_at`,`updated_at`) VALUES (3,'화요일 1시간 공부\n하기',10000.0,50000.0,'ACTIVE','2024-10-03 16:53:58',NULL);
INSERT INTO challenge (`id`,`title`,`deposit_min`,`deposit_max`,`status`,`created_at`,`updated_at`) VALUES (4,'수요일 1시간 공부\n하기',10000.0,50000.0,'ACTIVE','2024-10-03 16:53:58',NULL);
INSERT INTO challenge (`id`,`title`,`deposit_min`,`deposit_max`,`status`,`created_at`,`updated_at`) VALUES (5,'목요일 1시간 공부 하기',10000.0,50000.0,'ACTIVE','2024-10-03 16:53:58',NULL);
INSERT INTO challenge (`id`,`title`,`deposit_min`,`deposit_max`,`status`,`created_at`,`updated_at`) VALUES (6,'금요일 1시간 공부 하기',10000.0,50000.0,'ACTIVE','2024-10-03 16:53:58',NULL);
INSERT INTO challenge (`id`,`title`,`deposit_min`,`deposit_max`,`status`,`created_at`,`updated_at`) VALUES (7,'토요일 1시간 공부\n하기',10000.0,50000.0,'ACTIVE','2024-10-03 16:53:58',NULL);
INSERT INTO challenge (`id`,`title`,`deposit_min`,`deposit_max`,`status`,`created_at`,`updated_at`) VALUES (8,'일요일 1시간 공부\n하기',10000.0,50000.0,'ACTIVE','2024-10-03 16:53:58',NULL);
INSERT INTO challenge (`id`,`title`,`deposit_min`,`deposit_max`,`status`,`created_at`,`updated_at`) VALUES (9,'노출되면 안 되는 챌린지',12345.0,12456.0,'DEACTIVE','2024-10-03 17:04:24',NULL);


# 챌린지 스케쥴 테이블
CREATE TABLE `user_challenge_schedule` (
                                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                           `challenge_id` bigint(20) NOT NULL,
                                           `deposit_id` bigint(20) NOT NULL,
                                           `schedule_date` date NOT NULL COMMENT '챌린지 스케줄 날짜',
                                           `hour` int(11) NOT NULL COMMENT '스케줄 시간 (시간 단위)',
                                           `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                                           `updated_at` datetime DEFAULT NULL,
                                           PRIMARY KEY (`id`),
                                           KEY `deposit_idx` (`deposit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='유저별 챌린지 스케줄 테이블';


```


3. 보증금 관련

```SQL

# 보증금 결제 이력
CREATE TABLE `user_deposit_history` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `user_id` int(11) NOT NULL,
                                        `challenge_id` bigint(20) NOT NULL COMMENT '참여한 챌린지 ID',
                                        `pay_amount` decimal(10,1) NOT NULL COMMENT '실제 결제 금액',
                                        `deposit_amount` decimal(10,1) NOT NULL COMMENT '챌린지 참여로 적용된 보증금 금액',
                                        `repay_amount` decimal(10,1) DEFAULT NULL COMMENT '목표 달성으로 환급된 보증금',
                                        `penalty_amount` decimal(10,1) DEFAULT NULL COMMENT '목표 달성 실패로 차감된 보증금',
                                        `status` tinyint(1) NOT NULL COMMENT '보증금 상태 - 1: 도전 중, 2: 성공(목표 달성, 패널티 X), 3: 실패:(목표 일부 달성, 패널티 O)',
                                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `updated_at` datetime DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `search_idx` (`user_id`,`challenge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='보증금 이력 테이블';


```

