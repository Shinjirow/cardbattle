package E2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

/**
 * カードを集約するクラス
 */
public class Cards extends Object {

    /**
     * カードを集約しているクラス
     * 値で取り出せるようにするため、あえてMapで実装している
     * 時間があれば自分でコレクションを実装すればよかったように思う
     */
    private Map<Integer, Card> cards = new HashMap<Integer, Card>();

    /**
     * コンストラクタ
     * 初期化を行う
     */
    public Cards() {
        this.init();

        return;
    }

    /**
     * 初期化を行う
     */
    private void init() {
        IntStream.range(1, 14).forEach(i -> cards.put(i, new Card(i)));

        return;
    }

    /**
     * 自身のカードの情報をアップデートする
     * 具体的には評価値とかなんやらする
     *
     * @param myCard   - 自身のカードの情報 使ったかどうかがint型で入っている
     * @param yourCard - 相手のカードの情報 使ったかどうかがint型で入っている
     */
    public void update(int[] myCard, int[] yourCard) {
        assert myCard.length == yourCard.length : "incorrect array length between myCard :" + myCard.length + " and yourCard :" + yourCard.length;

        IntStream.range(1, myCard.length).forEach(myCardNumber -> cards.get(myCardNumber).update(myCard[myCardNumber], yourCard));

        return;
    }

    /**
     * 提出するカードを決める
     *
     * @param count 今何回目か
     * @return 提出したいカード
     */
    public Card determineSubmitCard(int count) {
        int timing = 6;
        Card card;
        if (count > timing) {
            card = this.findHighestElement();
        } else {
            card = this.findLowestElement();
        }

        return card;
    }

    /**
     * 自身の持つカードのなかからもっとも評価値が高い要素を取得する
     *
     * @return もっとも評価値が高い要素
     */
    private Card findHighestElement() {
        return this.cards.entrySet().stream().map(Entry::getValue).filter(aCard1 -> !aCard1.isUsed()).reduce(new Card(true, -1), (a, b) -> {
            Comparator<Card> comp = Card.comparator();

            if (comp.compare(a, b) < 0) return b;

            return a;
        });
    }

    /**
     * 自身の持つカードのなかからもっとも評価値が低い要素を取得する
     *
     * @return もっとも評価値が低い要素
     */
    private Card findLowestElement() {
        return this.cards.entrySet().stream().map(Entry::getValue).filter(aCard1 -> !aCard1.isUsed()).reduce(new Card(true, 114514), (a, b) -> {
            Comparator<Card> comp = Card.comparator();

            if (comp.compare(a, b) > 0) return b;

            return a;
        });
    }

    /**
     * 自身を文字列にして応答する
     *
     * @return 自身の文字列
     */
    @Override
    public String toString() {
        StringBuilder aBuffer = new StringBuilder();
        this.cards.forEach((k, v) -> {
            aBuffer.append(v);
            aBuffer.append(", ");
        });
        return aBuffer.toString();
    }
}
