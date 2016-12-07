package Slovnik;

import java.util.Comparator;

/**
 * Třída pro porovnání distancí.
 */
public class MyDistanceComp implements Comparator<Distance> {

    /**
     * Metoda porovná distance dvou objektů.
     * @param d1 distanceo objektu jedna.
     * @param d2 distance objektu dva.
     * @return v případě, že je distance objektu jedna větší než dva, tak vrátí -1.
     * V opračném případě vrátí 1. V případě, že něco selže, tak vrátí 0.
     */

    @Override
    public int compare(Distance d1, Distance d2) {
        if(d1.getDistance() < d2.getDistance()){
            return -1;
        }
        else if(d1.getDistance() > d2.getDistance()){
            return 1;
        }
        else{
            return 0;
        }
    }

}
