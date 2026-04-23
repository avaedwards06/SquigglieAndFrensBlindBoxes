package src.main.java;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class UserData {
    private final DoubleProperty walletBallance = new SimpleDoubleProperty(500.0);
    private final ObservableList<BlindBox> unopenedBoxes = FXCollections.observableArrayList();
    private final ObservableList<Prize> prizes = FXCollections.observableArrayList();
    
    public ObservableList<Prize> getPrizes() { 
        return prizes; 
    }
    public ObservableList<BlindBox> getUnopenedBoxes(){
        return unopenedBoxes;
    }
    public DoubleProperty balanceProperty(){
        return walletBallance;
    }

    public double getBalance(){
        return walletBallance.get();
    }

    public void setBalance(double amount){
        walletBallance.set(amount);
    }
}
