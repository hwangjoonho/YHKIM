package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            System.out.println("-======-----------------=======");
            Team team = new Team();
            team.setName("TeamA22");
            em.persist(team);
            System.out.println("-======------------------=======");

            Member member = new Member();
            member.setUsername("member1");
//            member.setTeamId(team.getId());   // 단방향
//        member.changeTeam(team);   // *** 양방향 연관성 주인 부분
        team.addmember(member);     // 편의 메서드 둘중하나 선택 / 무한루프 주의
            // 주인이 아닌 곳에 add 할 경우 null 현상
//            Team team = new Team();
//            team.setName("TeamA");
//            team.getMembers().add(member);
//            em.persist(team);

            System.out.println("-=============");
            em.persist(member);

            System.out.println("-=============");

//      team.getMembers().add(member);  // ***  양방향 연관성 주인 아닌 부분 / 주인 아닌곳 setter에 넣기

//            em.flush();
//            em.clear();

            System.out.println("_________________________________________");
            Team findTeam = em.find(Team.class, team.getId());  //1차 캐시
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }
            System.out.println("_________________________________________");

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
