/* @author Neptune */
import java.util.HashMap;
import java.util.Iterator;

public class Item{
    // Itemname , Belongs_to_who
    static HashMap<String, Object> treasure;
    // 是每個道具一開始就都有歸屬於誰？？    還是只要看他有沒有被撿走？？？？
    // Itemname , Who_can_get
    // HashMap<String, Object> whose;
    static HashMap<String, Integer> existTime;


    public Item() {
        createItems();
    }

    public void createItems(){
        treasure = new HashMap<>();
        existTime = new HashMap<>();
        makeItem("Abalone");
        makeItem("Hot_Girl");
        makeItem("Hot_Dog");
        makeItem("Sexy_Fish");
        makeItem("Shark_Wing");
        makeItem("30CM_Dick");
    }

    public void makeItem(String item){
        treasure.put(item, null);
        existTime.put(item,0);
    }

    public boolean hasItem(String item){
        return treasure.containsKey(item);
    }


    public String getItem(String item,String name){
        if(treasure.get(item)==null){
            treasure.replace(item, name);
            Thread tt = new Thread( new DropItem(item) );
            tt.start();
            return "Yes "+name;
        }else if(!treasure.get(item).equals(name)){
            return "No "+name;
        }else
            return "It's yours now!!!";

    }

    public String CallReleaseItem(String item,String name){
        if(treasure.get(item)==null){
            treasure.replace(item, name);
            return "It's not yours!!!!";
        }else if(!treasure.get(item).equals(name)){
            return "It's not yours!!!!";
        }else
            treasure.replace(item, null);
            existTime.replace(item, 0);
            return name + " release the " + item;
    }

    public static void ReleaseItem(String item){
        treasure.replace(item, null);
    }

    public String ItemStates() {
        String item_states = "";
        Iterator it =treasure.keySet().iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            item_states += key + " ";
            if(treasure.get(key)==null){
                item_states += "NO 0\n";
            }else
                item_states += "Yes " + treasure.get(key) + "\n";
        }
        return item_states;
    }

    public String ClientItemInfo(String name){
        String item_states = "";
        Iterator it = treasure.keySet().iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            try{
            if(treasure.get(key)!=null && treasure.get(key).equals(name)){
                item_states += key + " Yes " + existTime.get(key) + "\n";
            }else
                item_states += key + " NO\n";
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return item_states;
    }
}
