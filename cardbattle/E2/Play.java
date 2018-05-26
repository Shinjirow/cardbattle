package E2;

import java.util.Arrays;

public class Play extends Object implements P.Player {

    private boolean DEBUG = false;

    private int[] expectedValue = new int[14];


    public int call(int[] myCard, int[] yourCard, int[] myLog, int[] yourLog, double myLastScore, double yourLastScore, double myTotalScore, double yourTotalScore, int count, long timeLeft) {

        this.fillExpectedValue(myCard, yourCard);

        if (DEBUG) Arrays.stream(this.expectedValue).forEach(System.err::println);

        int elementNumber;
        int timing = 6;
        if (count > timing) elementNumber = this.getHighestExpectedElement(myCard, this.expectedValue);
        else elementNumber = this.getLowestExpectedElement(myCard, this.expectedValue);

        return elementNumber;
    }

    private void fillExpectedValue(int[] myCard, int[] yourCard){
        for (int myCardNumber = 1; myCardNumber < myCard.length; myCardNumber++) {
            if (myCard[myCardNumber] != 0) { //すでに使用済みのカードは
                this.expectedValue[myCardNumber] = 0; //期待値0にして
                continue; //今後の処理をスルーする
            }
            int winRate = 0;
            for (int yourCardNumber = 1; yourCardNumber < yourCard.length; yourCardNumber++) {
                if (yourCard[yourCardNumber] == 0) {
                    if (myCardNumber == yourCardNumber) {
                        winRate++;
                    } else {
                        if (Calculator.canDefeat(myCardNumber, yourCardNumber)) {
                            winRate += 2;
                        }
                    }
                }
            }
            //if(DEBUG) System.err.println("cardNumber = " + myCardNumber + ", winrate = " + winRate + ", potential = " + Calculator.getPotential(myCardNumber) + ", expected = " + winRate * Calculator.getPotential(myCardNumber));
            this.expectedValue[myCardNumber] = winRate * Calculator.getPotential(myCardNumber);
        }
    }

    private int getHighestExpectedElement(int[] myCard, int[] expectedValue) {
        int elementNumber = 0;
        int maxValue = -1;
        for (int i = 1; i < expectedValue.length; i++) {
            if (myCard[i] != 0) continue;

            if (maxValue < expectedValue[i]) {
                maxValue = expectedValue[i];
                elementNumber = i;
            }
        }
        return elementNumber;
    }

    private int getLowestExpectedElement(int[] myCard, int[] expectedValue) {
        int elementNumber = 0;
        int minValue = 114514;
        for (int i = 1; i < expectedValue.length; i++) {
            if (myCard[i] != 0) continue;

            if (minValue > expectedValue[i]) {
                minValue = expectedValue[i];
                elementNumber = i;
            }
        }
        return elementNumber;
    }
}
