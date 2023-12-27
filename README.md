# Junit Bank App

### Jpa LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
 
'''java
  @CreatedDate // 이 설정 --> Insert 할 때 날짜가 자동으로 들어감
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate // 이 설정 --> Insert, Update 할 때 날짜가 자동으로 들어감
  @Column(nullable = false)
  private LocalDateTime updatedAt;
'''