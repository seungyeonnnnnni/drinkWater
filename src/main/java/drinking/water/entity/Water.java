package drinking.water.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Water {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waterId;

    @ManyToOne
    private User user;

    private int cupSize; //capacity
    private int remainCup;

    private int current;
    private int goal;

    @OneToMany(mappedBy = "water")
    private List<DrinkStatus> drinkStatus = new ArrayList<>();

    public Long getWaterId() {
        return waterId;
    }

    public void setWaterId(Long waterId) {
        this.waterId = waterId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCupSize() {
        return cupSize;
    }

    public void setCupSize(int cupSize) {
        this.cupSize = cupSize;
    }

    public int getRemainCup() {
        return remainCup;
    }

    public void setRemainCup(int remainCup) {
        this.remainCup = remainCup;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }


}