package blockchain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(20);
        List<Miner> miners = List.of(
                new Miner(0), new Miner(0), new Miner(0),
                new Miner(0), new Miner(0), new Miner(0),
                new Miner(0), new Miner(0), new Miner(0),
                new Miner(0), new Miner(0), new Miner(0),
                new Miner(0), new Miner(0), new Miner(0)
        );
        List<Wallet> wallets = List.of(
                new Wallet("miner1", miners.get(0)), new Wallet("miner2", miners.get(1)), new Wallet("miner3", miners.get(2)),
                new Wallet("miner4", miners.get(3)), new Wallet("miner5", miners.get(4)), new Wallet("miner6", miners.get(5)),
                new Wallet("miner7", miners.get(6)), new Wallet("miner8", miners.get(7)), new Wallet("miner9", miners.get(8)),
                new Wallet("miner10", miners.get(9)), new Wallet("miner11", miners.get(10)), new Wallet("miner12", miners.get(11)),
                new Wallet("miner13", miners.get(12)), new Wallet("miner14", miners.get(13)), new Wallet("miner15", miners.get(14)),
                new Wallet("alice", miners.get(12)), new Wallet("bob", miners.get(5)), new Wallet("tom", miners.get(7)),
                new Wallet("nick", miners.get(6)), new Wallet("saral", miners.get(3)), new Wallet("jack", miners.get(9))
                );
        Spending spending = new Spending(wallets);
        for (int i = 0; i < 15; i++) {
            miners.get(i).setWallet(wallets.get(i));
        }
        int difficulty = 0;
        List<Callable<Block>> callables = new ArrayList<>(miners);
        List<Transaction> txs = new LinkedList<>();
        for (int i = 0; i < 15; i++) {
            try {
//                if (i > 0)
//                    miners.forEach(Miner::deserialize);
                for (var m: miners)
                    m.appendData(txs);
                long st = System.currentTimeMillis();
                Block b = es.invokeAny(callables);
                long ed = System.currentTimeMillis();
                miners.forEach(e -> e.accept(b));
                miners.forEach(Miner::clearDataPool);
//                miners.forEach(Miner::serialize);
                b.print();
                long ela = (ed - st);
                System.out.println("Block was generating for " + (ela / 1000) + " seconds");
                if (ela > 500) {
                    miners.forEach(Miner::decreaseDifficulty);
                    difficulty -= 1;
                    System.out.println("N was decreased to " + difficulty);
                } else if (ela < 250) {
                    miners.forEach(Miner::increaseDifficulty);
                    difficulty += 1;
                    System.out.println("N was increased to " + difficulty);
                }
                txs = spending.spend();
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        wallets.forEach(e -> {
//            try {
//                System.out.println(e.getOwner() + ": " + e.getBalance());
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//        });
        es.shutdown();
    }
}
