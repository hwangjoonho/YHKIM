package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {

    
    // 멀티쓰레드 환경에서 여러개가 동시에 접근할때는 HashMap 쓰면 안됨 => 동시 접근시 값이 꼬임 => ConcurrentHashMap 사용
    private static final Map<Long, Item> store = new HashMap<>(); //static   // 실전에서는 HashMap 사용 불가
//    private static final Map<Long, Item> store2 = new ConcurrentHashMap<>(); //static   // 실전에서는 HashMap 사용 불가
    private static long sequence = 0L;  //static
    
    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());

    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();

    }

}
