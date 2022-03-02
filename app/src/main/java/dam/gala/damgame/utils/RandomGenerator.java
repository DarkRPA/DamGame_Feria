package dam.gala.damgame.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomGenerator {
    private Set<Integer> usedNums = new HashSet<>();
    private int start;
    private int end;
    private List<Integer> numeros;

    public RandomGenerator(int start, int end) {
        this.usedNums = usedNums;
        this.start = start;
        this.end = end;
        this.generateRandomNumber();
    }

    public void regenerate(){
        this.generateRandomNumber();
    }

    // TODO FIX THIS
    private void generateRandomNumber(){
        while (usedNums.size() < end){
            int num = (int) (Math.random() * (end - start + 1) + start);
            usedNums.add(num);
        }

        this.numeros = new ArrayList<>(usedNums);
        Collections.shuffle(this.numeros);
    }

    public int getRandomNum(int index){
        return this.numeros.get(index);
    }
}
