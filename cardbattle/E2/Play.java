package E2;

import P.Player;

public class Play extends Object implements Player {

    /**
     * カードを管理するオブジェクト
     */
    private Cards cards = new Cards();

    public int call(int[] myCard, int[] yourCard, int[] myLog, int[] yourLog, double myLastScore, double yourLastScore, double myTotalScore, double yourTotalScore, int count, long timeLeft) {
        this.cards.update(myCard, yourCard);

        Card aCard = this.cards.determineSubmitCard(count);

        return aCard.returnValue();
    }
}
