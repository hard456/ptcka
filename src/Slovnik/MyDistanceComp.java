package Slovnik;

import java.util.Comparator;

/**
 * Created by HARD on 05.12.2016.
 */
public class MyDistanceComp implements Comparator<Distance> {

    @Override
    public int compare(Distance d1, Distance d2) {
        if(d1.getDistance() < d2.getDistance()){
            return -1;
        }
        else{
            return 1;
        }
    }

}
