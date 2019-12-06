package test.match;


import sun.applet.resources.MsgAppletViewer;

import java.util.*;

/**
 * author : zhouzijing
 * Date: 2019/12/4 16:16
 */
public class MatchManager {

    /**
     * 本次匹配不再使用队伍
     * 例如我按 12345一个一个查找 15组合时 只在5之后继续找可以匹配队伍12345不再尝试
     * 直到本次匹配出结果
     */

    static int teamMaxCount = 5;

    /**
     * 规则 20个5（包括5以下的队伍），凑出一对最合适的5 v 5
     */
    static Map<Integer, Group> allGroup = new TreeMap<>();




    /**
     *  所有红队
     */
    static Set<Team> redTeams = new HashSet<>();


    /**
     * 所有battle
     */
    static Set<Battle> battles = new HashSet<>();

    /**
     *  匹配出一个红队， 剩余所有队伍能组成的蓝队所有可能
     */
    static Map<Team, Set<Team>> blueTeams = new HashMap<>();

    public static void main(String[] args) {
//        map.put(1, new Group(1,3, 3000));
//        map.put(2, new Group(2,2, 1000));
//        map.put(3, new Group(3,4, 5000));
//        map.put(4, new Group(4, 1, 2000));
        allGroup.put(5, new Group(5, 5, 6000));
        allGroup.put(4, new Group(4, 1, 2000));
        allGroup.put(3, new Group(3, 4, 5000));
        allGroup.put(2, new Group(2, 2, 1000));
        allGroup.put(1, new Group(1, 3, 3000));

        for (Map.Entry<Integer, Group> entry : allGroup.entrySet()){
            Team team = new Team();
            function(team, entry.getKey() - 1);
        }


        //遍历出所有组成teamA的可能
        Iterator<Team> iterator = redTeams.iterator();
        while(iterator.hasNext()){
            Team next = iterator.next();
            for (Group group : next.getGroups()){
                System.out.println(group.getId());
            }
            System.out.println("------------------------");
        }
        System.out.println("+++++++++++++++++++");

        for (Map.Entry<Team, Set<Team>> entry : blueTeams.entrySet()){
            System.out.println("红队队伍为:");
            for (Group group : entry.getKey().getGroups()){
                System.out.println(group.getId());
            }
            System.out.println("红队战斗力为:"+ entry.getKey().getFight());
            System.out.println("**********************");
            System.out.println("蓝队队伍有:");
            Set<Team> value = entry.getValue();
            for (Team team : value) {
                int i = 0;
                i++;
                System.out.println("蓝队"+ i);
                for (Group group : team.getGroups()){
                    System.out.println(group.getId());
                }
                System.out.println("蓝队" + i + "的战斗力为:" + team.getFight());
                int abs = Math.abs(entry.getKey().getFight() - team.getFight());
                System.out.println("两者战斗力相差为:"+ abs);
                System.out.println("++++++++++++++++++++");
                Battle battle = new Battle();
                battle.setTeamA(entry.getKey());
                battle.setTeamB(team);
                battle.setDiffFight(abs);
                battles.add(battle);
            }
        }

        int min = 99999;

        for (Battle battle : battles){
            if (battle.getDiffFight() < min){
                min = battle.getDiffFight();
            }
        }

        for (Battle battle : battles){
            if (battle.getDiffFight() == min){
                System.out.println("可以创建战斗");
                System.out.println("TeamA的队伍为");
                for (Group group : battle.getTeamA().getGroups()){
                    System.out.println(group.getId());
                }
                System.out.println("-----------");
                System.out.println("TeamB的队伍为");
                for (Group group : battle.getTeamB().getGroups()){
                    System.out.println(group.getId());
                }
                System.out.println("两者战斗力相差" + battle.getDiffFight());
                System.out.println("+++++++++++");
            }
        }

    }


    public static boolean canMerge(Team team, Group group2) {


        //平均战力相差太大不能合并
        int abs = Math.abs(team.getAvgFight() - group2.getAvgFight());
        if (abs > 5000)
            return false;
        //超过人数上限不能合并
        int allCount1 = 0;
        for (Group group1 : team.getGroups()){
            allCount1 += group1.getCount();
        }
        if (allCount1 + group2.getCount() > teamMaxCount)
            return false;

        return true;
    }

    public static void merge(Team team, Group group){
        Team copy = new Team();
        copy.setGroups(team.getGroups());
        copy.setAvgFight(team.avgFight);
        copy.setCount(team.getCount());
        copy.setFight(team.getFight());


        copy.getGroups().add(group);
        int allFight = copy.getFight() + group.getFight();
        copy.setFight(allFight);

        int allCount = copy.getCount() + group.getCount();
        copy.setCount(allCount);

        copy.setAvgFight(allFight / allCount);

        //todo 放入已经合并池
        int allCount1 = 0;
        for (Group group1 : copy.getGroups()){
            allCount1 += group1.getCount();
        }
        if (allCount1 == 5){
            //合并完成
            redTeams.add(copy);
            Map<Integer, Group> mergeGroup = new HashMap<>();
            for (Group group1 : copy.getGroups()){
                mergeGroup.put(group1.getId(), group1);
            }
            match(copy, mergeGroup);
            return;
        }
        //如果没有 5个人还需要继续匹配
        function(copy, group.getId());

    }

    static void function(Team team, int groupId){
        Team copy = new Team();
        copy.setGroups(team.getGroups());
        copy.setAvgFight(team.avgFight);
        copy.setCount(team.getCount());
        copy.setFight(team.getFight());

        for (Map.Entry<Integer, Group> entry : allGroup.entrySet()){

            //只往后找
            if (entry.getKey() <= groupId){
                continue;
            }
            if (canMerge(team, entry.getValue())){
                merge(team, entry.getValue());
            }
        }

    }

    //当循环出teamA的时候， 再找出剩余队伍中所有的TeamB的可能
    static void match(Team teamA, Map<Integer, Group> mergeGroup){

        for (Map.Entry<Integer, Group> entry : allGroup.entrySet()){
            if (mergeGroup.containsKey(entry.getKey())){
                continue;
            }
            Team team = new Team();
            function2(team, entry.getKey() - 1, teamA, mergeGroup);
        }

    }



    static void function2(Team team, int groupId, Team teamA, Map<Integer, Group> mergeGroup){
        Team copy = new Team();
        copy.setGroups(team.getGroups());
        copy.setAvgFight(team.avgFight);
        copy.setCount(team.getCount());
        copy.setFight(team.getFight());

        for (Map.Entry<Integer, Group> entry : allGroup.entrySet()){
            if (mergeGroup.containsKey(entry.getKey())){
                continue;
            }
            //只往后找
            if (entry.getKey() <= groupId){
                continue;
            }
            if (canMerge(team, entry.getValue())){
                merge2(team, entry.getValue(), teamA, mergeGroup);
            }
        }

    }

    public static void merge2(Team team, Group group, Team teamA, Map<Integer, Group> mergeGroup){
        Team copy = new Team();
        copy.setGroups(team.getGroups());
        copy.setAvgFight(team.avgFight);
        copy.setCount(team.getCount());
        copy.setFight(team.getFight());


        copy.getGroups().add(group);
        int allFight = copy.getFight() + group.getFight();
        copy.setFight(allFight);

        int allCount = copy.getCount() + group.getCount();
        copy.setCount(allCount);

        copy.setAvgFight(allFight / allCount);

        //todo 放入已经合并池
        int allCount1 = 0;
        for (Group group1 : copy.getGroups()){
            allCount1 += group1.getCount();
        }
        if (allCount1 == 5){
            //合并完成
            if (!blueTeams.containsKey(teamA)){
                Set<Team> blues = new HashSet<>();
                blues.add(copy);
                blueTeams.put(teamA, blues);
            }
            else {
                Set<Team> blues = blueTeams.get(teamA);
                blues.add(copy);
            }
            return;
        }
        //如果没有 5个人还需要继续匹配
        function2(copy, group.getId(), teamA, mergeGroup);

    }



}
