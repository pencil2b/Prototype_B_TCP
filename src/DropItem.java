/* @author Neptune */
import java.util.HashMap;

public class DropItem implements Runnable {
    String object;
    int count = 5;

    public DropItem(String object) {
        this.object = object;
    }
    public int getCount() {
        return count;
    }
    @Override
    public void run() {
        for (; count > 0; count--) {
            try {
                Item.existTime.replace(object,count);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        Item.existTime.replace(object,count);
        Item.ReleaseItem(object);

    }

}
