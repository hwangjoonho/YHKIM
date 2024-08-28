package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

//    ------------------------- 多 : 多 관계 / 실무 사용 불가능-------------------------------------
//    @ManyToMany(mappedBy = "products")
//    private List<Member> members = new ArrayList<>();

    // -----------------------------多 : 多 관계 -> 맵핑 테이블 엔티티로 승격-------------------------------------

    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();
    // ---------------------------------------------------------------------------------------

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
