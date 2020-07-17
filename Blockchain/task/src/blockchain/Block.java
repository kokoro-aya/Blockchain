package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Block implements Serializable {
    private final int creator;
    private final int id;
    private final long timestamp;
    private final long magicNumber;
    private final String prevHash;
    private final String thisHash;
    private final long generationTime;

    private final List<Transaction> tx;

    Block(int creator, int id, String prevHash, int zeros, List<Transaction> tx) {
        this.creator = creator;
        this.id = id + 1;
        this.prevHash = prevHash;
        long st = System.currentTimeMillis();
        long ts = new Date().getTime();
        long mn = new Random().nextLong();
        String hs;
        hs = StringUtil.applySha256(id + " " + ts + " " + mn + " " + prevHash + " " + tx.hashCode());
        ;
        while (!StringUtil.isStartWithNZeros(hs, zeros)) {
            ts = new Date().getTime();
            mn = new Random().nextLong();
            hs = StringUtil.applySha256(id + " " + ts + " " + mn + " " + prevHash + " " + tx.hashCode());
        }
        this.timestamp = ts;
        this.magicNumber = mn;
        this.thisHash = hs;
        this.tx = new LinkedList<>(tx);
        long fi = System.currentTimeMillis();
        generationTime = (fi - st) / 1000;
    }

    public int getCreator() {
        return creator;
    }

    int getId() {
        return id;
    }

    long getTimestamp() {
        return timestamp;
    }

    String getPrevHash() {
        return prevHash;
    }

    String getThisHash() {
        return thisHash;
    }

    public List<Transaction> getTx() {
        return tx;
    }

    void print() {
        System.out.println("Block:");
        System.out.println("Created by miner # " + creator);
        System.out.println("miner" + creator + " gets 100 VC");
        System.out.println("Id: " + id);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Magic number: " + magicNumber);
        System.out.println("Hash of the previous block:");
        System.out.println(prevHash);
        System.out.println("Hash of the block:");
        System.out.println(thisHash);
        System.out.println("Block data:");
        if (tx.size() == 1) {
            System.out.println("No transactions");
        } else {
            tx.forEach(e -> {
                if (e.src != null) {
                    System.out.println(e.toString());
                }
            });
        }
//        System.out.println("Block was generating for " + generationTime + " seconds");
    }
}
