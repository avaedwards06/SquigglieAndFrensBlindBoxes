package src.main.java;

public class Prize {
    private String name;
    private String imagePath;
    private String rarityLabel;
    private double sellPrice;
    private int weight;

    public Prize(String name, String rarityLabel, double sellPrice, int weight) {
        this.name = name;
        this.imagePath = "/images/" +name.toLowerCase().replace(" ", "_") + ".png";
        this.rarityLabel = rarityLabel;
        this.sellPrice = sellPrice;
        this.weight = weight;
    }

    // Found out you can squish these, pretty nice!
    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public String getRarityLabel() { return rarityLabel; }
    public double getSellPrice() { return sellPrice; }
    public int getWeight(){return weight; }

}
