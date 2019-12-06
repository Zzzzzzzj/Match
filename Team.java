package test.match;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zhouzijing
 * Date: 2019/12/4 16:37
 */
public class Team {

    List<Group> groups = new ArrayList<>();

    int fight;

    int avgFight;

    //总人数
    int count;


    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public int getFight() {
        return fight;
    }

    public void setFight(int fight) {
        this.fight = fight;
    }

    public int getAvgFight() {
        return avgFight;
    }

    public void setAvgFight(int avgFight) {
        this.avgFight = avgFight;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
