package game;

public class BeginningOfTheGame implements GameState {
    private BattleShip battleShip;
    private PlayerScreen player1;
    private PlayerScreen player2;

    BeginningOfTheGame(BattleShip battleShip,PlayerScreen player1,PlayerScreen player2){
        this.player1 = player1;
        this.player2 = player2;
        this.battleShip = battleShip;
    }

    public void player1Turn (){
        player1.getSelfGrid().setSelfGridListener(true);
    }

    public void player2turn (){
        player2.getSelfGrid().setSelfGridListener(true);
        battleShip.setState(battleShip.getMiddleOfTheGame());
    }

}