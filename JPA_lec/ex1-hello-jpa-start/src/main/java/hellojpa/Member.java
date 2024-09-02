package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
// @Table(name = "MBR")
// @SequenceGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
//        initialValue = 1, allocationSize = 1)    // allocationSize : 미리 시퀀스 50개 땡겨와서 영속성으로 관리 = DB 시퀀스 조회 최소화
// @TableGenerator(      // 비추천
//        name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCES",
//        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,
//            generator = "MEMBER_SEQ_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.TABLE,
//            generator = "MEMBER_SEQ_GENERATOR")

    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;
//    -------------------------------------------- 즉시 로딩과 지연 로딩 ----------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

//------------------------------------------------------------------------------

//    -------------------------------------------- 多 : 1 단방향 매핑 ----------------------------------------------------------------
//    @ManyToOne      // (주체=this=Member)Many to (대상=Team)One
//    @JoinColumn(name = "TEAM_ID")   // mapped by가 없으므로 연관관계의 주인이라는 뜻 / 1대 多 관계에서 '多'쪽을 주인으로 잡아야 깔끔하게 설계가 가능
//    private Team team;
//------------------------------------------------------------------------------
//    ----------------- 1 대 多 양방향 매핑 ---------------
//    @ManyToOne
//    @JoinColumn(name = "TEAM_ID",insertable = false, updatable = false)  //<<---- Team에서    관리하기에 조회만 가능하도록 수동조작
//    private Team team;
//--------------------------------------------------------

    //    -------------------- 1 : 1 관계 ----------------------------
//    @OneToOne
//    @JoinColumn(name = "LOCKER_ID")
//    private Locker locker;

//    ------------------------- 多 : 多 관계 / 실무 사용 불가능-------------------------------------
//    @ManyToMany
// -----------------------------多 : 多 관계 -> 맵핑 테이블 엔티티로 승격-------------------------------------

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();
    // ---------------------------------------------------------------------------------------




    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    } //연관관계 편의 메서드로 치환 // *** 널체크 필요
//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    } // 양쪽에 연관관계 편의 메서드 존재시 무한루프 주의 -> 한쪽만 해주자



    //    @Column(name = "TEAM_ID")
//    private Long teamId;

    //    public Long getTeamId() {
//        return teamId;
//    }
//
//    public void setTeamId(Long teamId) {
//        this.teamId = teamId;
//    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


//    private Integer age;
//    @Enumerated(EnumType.STRING)
//    private RoleType roleType;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//
//    private LocalDate testLocalDate;
//    private LocalDateTime testLocalDateTime;
//    @Lob
//    private String description;

    public Member() {
    }
    
    // 타입간의 차이 : 객체(object) vs 기본(primitive)
    // 타입에 따른 참조 관계로 객체는 주소를 참조 -> 동일 참조시 심각한 에러 발생 -> setter 삭제 후 constructor를 통한 생성 필요 즉, 필요시마다 new로 객체 신규 생성하는 로직 필요
    // 객체는 서로 다르므로 값 자체(동등성) 비교시 equals override 필요
    
//   

}
