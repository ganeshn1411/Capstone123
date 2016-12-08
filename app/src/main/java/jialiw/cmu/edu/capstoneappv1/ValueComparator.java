package jialiw.cmu.edu.capstoneappv1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dan on 11/22/2016.
 */

public class ValueComparator implements Comparator<String> {
    private Map<String,MainProfileActivity.ActivityOrderObject> map;

    ValueComparator(Map<String, MainProfileActivity.ActivityOrderObject> map){
        this.map = map;
    }
    @Override
    public int compare (String key1, String key2) {
//        Comparable value1 = map.get(key1);
//        Comparable value2 = map.get(key2);
//        return value1.compareTo(value2);
        Comparable value1 = map.get(key1).getOrder();
        Comparable value2 = map.get(key2).getOrder();
        return value1.compareTo(value2);

    }
}
