package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id @GeneratedValue
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)  // (첨부파일-경로,파일,옵션) 관계와 같이 소유자가 하나일 때만 사용
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)    // orphanRemoval : 컬렉션에서 빠질경우 삭제됨
    private List<Child> ChildList = new ArrayList<>();

    public List<Child> getChildList() {
        return ChildList;
    }

    public void setChildList(List<Child> childList) {
        ChildList = childList;
    }

    public void addChild(Child child){
        ChildList.add(child);
        child.setParent(this);
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
