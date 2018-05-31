package E2;

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * 1枚のカードのクラスである
 */
public class Card extends Object implements Comparable<Card> {

    /**
     * 使用済みか
     */
    private boolean used;

    /**
     * カードの値
     */
    private int value;

    /**
     * カードが勝った時に得られる得点
     */
    private int potential;

    /**
     * このカードを今出した時に得られる期待値が入る
     */
    private int expectedValue;

    /**
     * コンストラクタ
     *
     * @param value
     */
    public Card(int value) {
        assert (0 < value && value < 14) : "incorrect number of initialize value";
        this.value = value;
        this.potential = (value == 1 ? 1 : 15 - value);
        this.used = false;
        return;
    }

    /**
     * 特殊なコンストラクタ 要素を評価順に集約する際にスタート地点として使う
     *
     * @param debug         - 一般的に使われたくないので trueじゃないとassertが文句言える
     * @param expectedValue 評価値
     */
    public Card(boolean debug, int expectedValue) {
        assert debug : "意図しない使用方法です";

        this.expectedValue = expectedValue;
    }

    /**
     * すでにこのカードが使われているかどうか
     *
     * @return 使われているとtrue, いないならfalse
     */
    public boolean isUsed() {
        return this.used;
    }

    /**
     * 自身の情報についてアップデートを行う
     *
     * @param value     - 使われたかどうか 0じゃなかったらtrue
     * @param rivalCard - ライバルのカードが使われているかの情報
     */
    public void update(int value, int[] rivalCard) {
        this.expectedValue = 0;
        this.used = (value != 0);
        if (!this.used) this.evaluate(rivalCard);
        return;
    }

    /**
     * 自身のパフォーマンスについて評価を行う
     *
     * @param rivalCard - ライバルのカードの情報
     */
    private void evaluate(int[] rivalCard) {
        IntStream.range(1, rivalCard.length).filter(rivalCardNumber -> rivalCard[rivalCardNumber] == 0).forEach(rivalCardNumber -> {
            int eval = this.compareTo(new Card(rivalCardNumber));
            if (eval > 0) {
                this.expectedValue += this.potential * 2;
            } else if (eval == 0) {
                this.expectedValue += this.potential;
            }
        });
        return;
    }

    /**
     * 自身を文字列にして応答する
     *
     * @return 自身の文字列
     */
    @Override
    public String toString() {
        StringBuilder aBuffer = new StringBuilder();
        aBuffer.append("Card[");
        aBuffer.append(this.value);
        aBuffer.append("], Used = ");
        aBuffer.append(this.used);
        aBuffer.append(", Potential = ");
        aBuffer.append(this.potential);
        aBuffer.append(", ExpectedValue = ");
        aBuffer.append(this.expectedValue);
        return aBuffer.toString();
    }

    /**
     * 値を応答する
     *
     * @return
     */
    public int returnValue() {
        return this.value;
    }

    /**
     * 勝つかどうかで比較する
     *
     * @param aCard 相手のカード
     * @return {0 : 引き分けの場合, 正 : 勝ち, 負 : 負け}
     */
    public int compareTo(Card aCard) {
        if (this.value == aCard.value) return 0;
        else if (this.canDefeat(aCard)) return 1;

        return -1;
    }

    /**
     * 自身を評価値で比較するコンパレータを応答する
     *
     * @return Comparator
     */
    public static Comparator<Card> comparator() {
        return (a, b) -> {
            if (a.expectedValue == b.expectedValue) return 0;
            else if (a.expectedValue > b.expectedValue) return 1;
            else return -1;
        };
    }

    /**
     * 相手に勝てるかどうか判定する
     * 引き分けの場合はfalseになるので注意
     *
     * @param anotherCard - 相手のカード
     * @return 勝てる場合true, そうでない場合false
     */
    public boolean canDefeat(Card anotherCard) {
        boolean flag = false;
        if (this.value == 1) {
            if (anotherCard.value != 2) flag = true;
        } else if (this.value == 2) {
            if (anotherCard.value == 1) flag = true;
        } else {
            if (anotherCard.value != 1) if (this.value > anotherCard.value) flag = true;
        }
        return flag;
    }
}
