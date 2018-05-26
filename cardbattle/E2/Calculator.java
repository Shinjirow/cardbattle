package E2;

class Calculator extends Object {
    protected static boolean canDefeat(int myNumber, int yourNumber) {
        boolean flag = false;
        if (myNumber == 1) {
            if (yourNumber != 2) flag = true;
        } else if (myNumber == 2) {
            if (yourNumber == 1) flag = true;
        } else {
            if (yourNumber != 1) if (myNumber > yourNumber) flag = true;
        }
        return flag;
    }

    protected static int getPotential(int cardNumber) {
        return cardNumber == 1 ? 1 : 15 - cardNumber;
    }
}
