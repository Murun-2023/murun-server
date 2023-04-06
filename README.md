# murunApp-Server
뮤런앱 서버

## why?

- QueryDsl `insert`문제
- Rds 가끔씩 연결 안되는 문제
- kotlin compiler 문제

## 문제

- postman `LocalDateTime` 반환하면 [2023,4,7,00,12345]이런식으로 반환하는 것 해결. `@JsonFormat`적용
