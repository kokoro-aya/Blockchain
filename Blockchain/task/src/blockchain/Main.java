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
        List<Message> message1 = List.of(
                new Message("Tom", "Hey, I'm first!")
        );
        List<Message> message2 = List.of(
                new Message("Sarah", "It's not fair!"),
                new Message("Sarah", "You always will be first because it's your blockchain!"),
                new Message("Sarah", "Anyway, thank you for this amazing chat.")
        );
        List<Message> message3 = List.of(
                new Message("Tom", "You are welcome:)"),
                new Message("Nick", "Hey, Tom, nice chat")
        );
        List<Message> message4 = List.of(
                new Message("Tom", "Thank you!")
        );
        List<Messager> messages = List.of(
                new Messager(), new Messager(), new Messager(), new Messager()
                );
        messages.get(0).add(message1);
        messages.get(1).add(message2);
        messages.get(2).add(message3);
        messages.get(3).add(message4);
        int difficulty = 0;
        for (int i = 0; i < 5; i++) {
            try {
//                if (i > 0)
//                    miners.forEach(Miner::deserialize);
                if (i > 0) {
                    Messager currentMessages = messages.get(i - 1);
                    miners.forEach(e -> e.acceptMessage(currentMessages));
                }
                long st = System.currentTimeMillis();
                List<Callable<Block>> callables = new ArrayList<>(miners);
                Block b = es.invokeAny(callables);
                long ed = System.currentTimeMillis();
                miners.forEach(e -> e.accept(b));
//                miners.forEach(Miner::serialize);
                b.print();
                long ela = (ed - st);
                System.out.println("Block was generating for " + (ela / 1000) + " seconds");
                if (ela > 7500) {
                    miners.forEach(Miner::decreaseDifficulty);
                    difficulty -= 1;
                    System.out.println("N was decreased to " + difficulty);
                } else if (ela < 4500) {
                    miners.forEach(Miner::increaseDifficulty);
                    difficulty += 1;
                    System.out.println("N was increased to " + difficulty);
                }
                System.out.println();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
