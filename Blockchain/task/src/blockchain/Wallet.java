package blockchain;

import java.util.LinkedList;
import java.util.List;

public class Wallet {
    final private String owner;
    final private Miner miner;
    private List<Transaction> pool;

    Wallet(String of, Miner depend) {
        this.owner = of;
        this.miner = depend;
        this.pool = new LinkedList<>();
    }

    public String getOwner() {
        return owner;
    }

    public int getBalance() throws Exception {
        return miner.getAmountOf(owner) + pool.stream().map(e -> e.amount).reduce(Integer::sum).orElse(0);
    }

    public Transaction send(Wallet dest, int amount) throws Exception {
        if (dest != this && dest != null) {
            pool.add(new Transaction(this, dest, amount));
            return new Transaction(this, dest, amount);
        } else {
            return null;
        }
    }

    public void resetPool() {
        pool.clear();
    }

    @Override
    public String toString() {
        return owner;
    }
}
