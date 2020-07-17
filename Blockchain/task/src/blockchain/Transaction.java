package blockchain;

public class Transaction {
    Wallet src;
    Wallet dest;
    int amount;
    Transaction(Wallet from, Wallet to, int of) {
        src = from;
        dest = to;
        amount = of;
    }

    @Override
    public String toString() {
        return src + " sent " + amount + " VC to " + dest;
    }
}
