package src.main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlindBox {
    private String name;
    private double price;
    private String coverImagePath;
    private List<Prize> possiblePrizes = new ArrayList<>();
    private Random rand = new Random();

    public BlindBox(String name, double price, String coverImagePath){
        this.name = name;
        this.price = price;
        this.coverImagePath = coverImagePath;
    }
    public void addItem(String name, String rarityValue, double price, int weight) {
    possiblePrizes.add(new Prize(name, rarityValue, price, weight));
}
    public Prize open() {
        int totalWeight = possiblePrizes.stream().mapToInt(Prize::getWeight).sum();
        int roll = rand.nextInt(totalWeight);

        int cursor = 0;
        for (Prize prize : possiblePrizes) {
            cursor += prize.getWeight();
            if (roll < cursor) {
                return prize;
            }
        }
        return possiblePrizes.get(0);
    }
    public String getCoverImagePath() {
        return coverImagePath;
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
}
