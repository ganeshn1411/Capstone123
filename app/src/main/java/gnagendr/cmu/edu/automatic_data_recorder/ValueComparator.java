package gnagendr.cmu.edu.automatic_data_recorder;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Ganesh on 12/13/2016.
 */

public class ValueComparator implements Comparator<String> {
    private Map<String,MainActivity.ActivityOrderObject> map;

    ValueComparator(Map<String, MainActivity.ActivityOrderObject> map){
        this.map = map;
    }
    public int compare (String key1, String key2) {
        Comparable value1 = map.get(key1).getOrder();
        Comparable value2 = map.get(key2).getOrder();
        return value1.compareTo(value2);
    }
}
