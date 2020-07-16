package blockchain;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        for (int i = 0; i < 5; i++) {
            try {
                if (i > 0)
                    miners.forEach(Miner::deserialize);
                long st = System.currentTimeMillis();
                List<Callable<Block>> callables = new ArrayList<>(miners);
                Block b = es.invokeAny(callables);
                long ed = System.currentTimeMillis();
                miners.forEach(e -> e.accept(b));
                miners.forEach(Miner::serialize);
                b.print();
                long ela = (ed - st);
                System.out.println("Block was generating for " + (ela / 1000) + " seconds");
                if (ela > 7500) {
                    miners.forEach(Miner::decreaseDifficulty);
                    System.out.println("N was decreased by 1");
                } else if (ela < 4500) {
                    miners.forEach(Miner::increaseDifficulty);
                    System.out.println("N was increased by 1");
                }
                System.out.println();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
