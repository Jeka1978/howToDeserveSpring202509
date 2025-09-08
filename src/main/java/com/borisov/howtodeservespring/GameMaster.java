package com.borisov.howtodeservespring;


public class GameMaster {

    private Spider spider1 = ObjectFactory.getInstance().createObject(PaperSpider.class);

    private Spider spider2 = ObjectFactory.getInstance().createObject(StoneSpider.class);
    ;


//    private HistoricalService historicalService;

    private int battleId = 0;  // Счётчик боёв

    public void fight() {
        // Увеличиваем ID боя для каждого нового боя
        battleId++;
        System.out.println("Начинаем бой №" + battleId + " между " + spider1.getClass().getSimpleName() + " и " + spider2.getClass().getSimpleName() + "!");

        while (spider1.isAlive() && spider2.isAlive()) {
            RPSEnum move1 = spider1.fight();  // Передаём оппонента и battleId
            RPSEnum move2 = spider2.fight();  // Передаём оппонента и battleId

            // Сохраняем историю хода для каждого паука

            System.out.println("Ходы");
            System.out.println("----");
            System.out.printf("%10s : %-15s\n",
                    move1,
                    spider1.getClass().getSimpleName()
            );
            System.out.printf("%10s : %-15s\n",
                    move2,
                    spider2.getClass().getSimpleName()
            );

            // Логика боя
            if (move1 == RPSEnum.ROCK && move2 == RPSEnum.SCISSORS) {
                spider2.loseLife();
            } else if (move1 == RPSEnum.SCISSORS && move2 == RPSEnum.PAPER) {
                spider2.loseLife();
            } else if (move1 == RPSEnum.PAPER && move2 == RPSEnum.ROCK) {
                spider2.loseLife();
            } else {
                spider1.loseLife();
            }

            System.out.println("Жизни игроков:");
            System.out.println("--------------");
            System.out.printf("%10s : %-20s\n", spider1.getLives(), spider1.getClass().getSimpleName());
            System.out.printf("%10s : %-20s\n", spider2.getLives(), spider2.getClass().getSimpleName());
        }

        // Определяем победителя
        String winner = spider1.isAlive() ? spider1.getClass().getSimpleName() : spider2.getClass().getSimpleName();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(" Бой №" + battleId + " окончен! Победитель: " + winner);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
