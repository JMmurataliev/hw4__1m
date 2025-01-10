import java.util.Random;

public class Main {



    public static int bossHealth = 1300;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Lucky", "Golem", "Witcher", "Thor"};
    public static int[] heroesHealth = {280, 270, 260, 250, 240, 350, 245, 255};
    public static int[] heroesDamage = {20, 10, 15, 8, 12, 5, 18, 12};
    public static int roundNumber = 0;




    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int currentHP : heroesHealth) {
            if (currentHP > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        printStatistics();
        healing();
        thorStunnedBoss();


        if (heroesHealth[6] > 0) {
            for (int i = 0; i < heroesAttackType.length; i++) {
                if (heroesHealth[i] == 0) {
                    heroesHealth [i] = heroesHealth[6];
                    heroesHealth[6] = 0;
                    System.out.println("The Witcher gave his life to " + heroesAttackType[i]);
                    break;
                }
            }
        }



    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(2,10); // 2,3,4,5,6,7,8,9
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage + " (" + coeff + ")");
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }
    public static int healing() {
        int healing = 80;
        if (heroesHealth[3] <= 0) {
            return 0;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < 100 && heroesHealth[i] > 0 && i !=3) {
                heroesHealth[i] += healing;
            }
            System.out.println(heroesAttackType[i] + " Исцеленный герой! увеличенный ед. здоровья на:" + healing);
            break;
        }
        return 0;
    }


    public static void bossAttack() {
        Random random = new Random();
        int damageGolem = 0;
        for (int i = 0; i < heroesHealth.length; i++) {

            if (i == 4 && 0 == random.nextInt(4)) {
                System.out.println("Lucky is dodged 25%!");
                continue;
            }

            int damage = bossDamage;
            if (heroesHealth[5] > 0 && !heroesAttackType[i].equals(heroesAttackType[5])) {
                int take = Math.min(bossDamage / 5, heroesHealth[5]);
                heroesHealth[5] -= take;
                damageGolem += take;
                damage -= take;
            }

            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - damage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - damage;
                }
            }
        }
        System.out.println("Golem takes 1/5 of the damage coming from the boss to other players. - "  + damageGolem);
    }
    public static void thorStunnedBoss() {
        Random thor = new Random();
        boolean stun = thor.nextBoolean();
        if (stun) {
            bossDamage=0;
            System.out.println(heroesAttackType[7] + " stunned Boss");
        }else{
            bossDamage=50;
        }
    }


    public static void printStatistics() {
        System.out.println("ROUND: " + roundNumber + " -----------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] +
                    " damage: " + heroesDamage[i]);
        }
    }

}