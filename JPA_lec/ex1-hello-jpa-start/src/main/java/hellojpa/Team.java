package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@SequenceGenerator(
//        name = "TEAM_SEQ_GENERATOR",
//        sequenceName = "TEAM_SEQ", //매핑할 데이터베이스 시퀀스 이름
//        initialValue = 1, allocationSize = 1)    // allocationSize : 미리 시퀀스 50개 땡겨와서 영속성으로 관리 = DB 시퀀스 조회 최소화
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,
//            generator = "TEAM_SEQ_GENERATOR")
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "team")   // mappedBy에 연관관계의 주인 표시 / 여기서는 읽기만 가능
    private List<Member> members = new ArrayList<>();


    public void addmember(Member member) {
        member.setTeam(this);   // 다관계 적용 부분 (객체에 적용하기)
        members.add(member);    // 일관계 적용 부분 (컬렉션에 넣어주기)
    }
    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
