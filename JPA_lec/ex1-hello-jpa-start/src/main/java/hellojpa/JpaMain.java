package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member1.getId());

            System.out.println("m=" + m.getTeam().getClass());      // 값이 프록시로 떨어진다. 즉 껍데기만 먼저 가져온다.

            System.out.println("=============================");
            m.getTeam().getName();      // 값을 직접 조회하는 이 시점에 껍데기에 직접 값을 채워넣는다.
            System.out.println("=============================");

// ---------------------------------------- 객체 proxy 파트 --------- 즉 객체 프록시(getReference)나 DB 조회 후 캐싱(1차 캐시) 둘다 영속성 컨텍스트 1차 캐시와 같은 레벨로 반영 --------------------------------
//            Member member1 = new Member();                                      // 이후 사용시 먼저 사용된 값을 가져와서 사용하는 방식으로 운용
//            member1.setUsername("user1");
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setUsername("user2");
//            em.persist(member2);
//
//            em.flush();     // 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
//            em.clear();     // 영속성 컨텍스트를 완전히 초기화
//
//            Member m1 = em.getReference(Member.class, member1.getId());     // 프록시 조회 -> 영속성 컨텍스트에 반영
//            System.out.println("m1 = " + m1.getClass());
//            String username = m1.getUsername();     // 프록시 강제 초기화
//            System.out.println("username = " + username);
//
////            Member reference = em.getReference(Member.class, member2.getId());
////            System.out.println("reference = " + reference.getClass());
//
//            Member reference = em.find(Member.class, member1.getId());          // DB 조회 -> 영속성 컨텍스트에 반영
//            System.out.println("reference = " + reference.getClass());
//
//            // JPA에서는 같은 영속성 컨텍스트/ 같은 트랙잭션 레벨 안에서 조회시 항상 같다가 나와야한다.
//            System.out.println("a == a " + (m1 == reference));

//------------------------------------------------------------------------------------------------------------



//            Movie movie = new Movie();
//            movie.setDirector("aaaa");
//            movie.setActor("bbbb");
//            movie.setName("바람과함께사라지다");
//            movie.setPrice(10000);
//
//            em.persist(movie);

//            em.flush();
//            em.clear();

//            Item item = em.find(Item.class, movie.getId());
//            System.out.println("item = " + item);


//            System.out.println("-======-----------------=======");
//            Team team = new Team();
//            team.setName("TeamA22");
//            em.persist(team);
//            System.out.println("-======------------------=======");
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeamId(team.getId());   // 단방향
//        member.changeTeam(team);   // *** 양방향 연관성 주인 부분
//        team.addmember(member);     // 편의 메서드 둘중하나 선택 / 무한루프 주의
            // 주인이 아닌 곳에 add 할 경우 null 현상
//            Team team = new Team();
//            team.setName("TeamA");
//            team.getMembers().add(member);
//            em.persist(team);

//            System.out.println("-=============");
//            em.persist(member);
//
//            System.out.println("-===========????==");

//      team.getMembers().add(member);  // ***  양방향 연관성 주인 아닌 부분 / 주인 아닌곳 setter에 넣기

//            em.flush();
//            em.clear();

//            System.out.println("_________________________________________");
//            Team findTeam = em.find(Team.class, team.getId());  //1차 캐시
//            List<Member> members = findTeam.getMembers();
//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }
//            System.out.println("_________________________________________");

            //mappedBy 연관관계 주인 부분
//            Member findMember = em.find(Member.class, member.getId());
////                Member findMember = em.find(Member.class, "CCSAEC23"); pk로만 찾아야한다.
//            System.out.println("-------0000000000000000000000");
//            System.out.println("findMember = " + findMember.getUsername());
//////            Team findTeamId = findMember.getTeamId();
//////            Team findTeam = em.find(Team.class, findTeamId);
//////            Team findTeam = findMember.getTeam(); => 연관관계 이용
//////            System.out.println("findTeam.getName() = " + findTeam.getName());
//            List<Member> members = findMember.getTeam().getMembers();
//
//            for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//            }
//            System.out.println("끝ㅌㅌㅌㅌㅌ");




            // 단방향 find 조회
//            Team newTeam = em.find(Team.class, 100L);
//            findMember.setTeam(newTeam);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
