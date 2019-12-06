package test.match;

/**
 * author : zhouzijing
 * Date: 2019/12/6 15:29
 */
public class Battle {

    Team teamA;

    Team teamB;

    //两队相差战斗力
    int diffFight;

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public int getDiffFight() {
        return diffFight;
    }

    public void setDiffFight(int diffFight) {
        this.diffFight = diffFight;
    }
}
