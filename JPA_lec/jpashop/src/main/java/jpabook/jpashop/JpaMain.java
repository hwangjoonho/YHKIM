package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 로직 나눠서 다시 조회 하는 방향으로 설계하는것부터 해보자
            // order에서 orderitem 조회시 item은>>>???? item과 orderitem도 양방향???
            // 주인이 아닌 곳에 양방향 처리 안하고 이렇게 하면 된다
            Order order = new Order();
            em.persist(order);
            // 위처럼 일관계(주인x : 선언만) vs 다관계(주인 : 양방향처리까지)
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);  // 다관계(주인) 주체로 양방향 처리
            em.persist(orderItem);

//            order.addOrderItem(new OrderItem());  주인이 아닌 곳에서 주체적으로 메서드 작성할 필요는 없긴하다 그냥 위처럼 해도된다.


//            order.setMember("황준호");

//            order.setOrderDate("2024-08-20");
//            order.setStatus("ORDER");
            order.set
            em.persist(order);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
