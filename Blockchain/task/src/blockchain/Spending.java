package blockchain;

import java.util.*;

public class Spending {
    Random r = new Random();
    private List<Wallet> wallets;

    Spending(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public List<Transaction> spend() throws Exception {
        int[] remains = new int[wallets.size()];
        for (int i = 0; i < wallets.size(); i++) {
            remains[i] = wallets.get(i).getBalance();
        }
        List<Transaction> txs = new LinkedList<>();
        int cnt = 0;
        while (cnt < 9) {
            int src = r.nextInt(wallets.size());
            int dest = r.nextInt(wallets.size());
            int ac = r.nextInt(30);
            if (remains[src] >= ac) {
                txs.add(new Transaction(wallets.get(src), wallets.get(dest), ac));
                remains[src] -= ac;
            }
            cnt++;
        }
        return txs;
    }
}
