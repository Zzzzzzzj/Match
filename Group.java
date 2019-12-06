package test.match;

/**
 * author : zhouzijing
 * Date: 2019/12/4 16:23
 */
public class Group{

    Group(int id, int count, int fight){
        this.count = count;
        this.fight = fight;
        this.avgFight = fight / count;
        this.id = id;
    }


    //队伍唯一id
    private int id;

    //人数
    private int count;

    //战斗力
    private int fight;

    //平均战力
    private int avgFight;

    public int getAvgFight() {
        return avgFight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFight() {
        return fight;
    }

    public void setFight(int fight) {
        this.fight = fight;
    }
}
