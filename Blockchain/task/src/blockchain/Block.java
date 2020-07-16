package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Block implements Serializable {
    final private int creator;
    final private int id;
    private final long timestamp;
    private final long magicNumber;
    final private String prevHash;
    private final String thisHash;
    private long generationTime;
    final private Messager messager;

    Block(int creator, int id, String prevHash, int zeros, Messager messager) {
        this.creator = creator;
        this.id = id + 1;
        this.prevHash = prevHash;
        this.messager = messager;
        long st = System.currentTimeMillis();
        long ts = new Date().getTime();
        long mn = new Random().nextLong();
        String hs;
        if (messager == null) {
            hs = StringUtil.applySha256(id + " " + ts + " " + mn + " " + prevHash + "\n" + "empty");
        } else {
            hs = StringUtil.applySha256(id + " " + ts + " " + mn + " " + prevHash + "\n" + messager.toString());
        };
        while (!StringUtil.isStartWithNZeros(hs, zeros)) {
            ts = new Date().getTime();
            mn = new Random().nextLong();
            if (messager == null) {
                hs = StringUtil.applySha256(id + " " + ts + " " + mn + " " + prevHash + "\n" + "empty");
            } else {
                hs = StringUtil.applySha256(id + " " + ts + " " + mn + " " + prevHash + "\n" + messager.toString());
            }
        }
        this.timestamp = ts;
        this.magicNumber = mn;
        this.thisHash = hs;
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

    void print() {
        System.out.println("Block:");
        System.out.println("Created by miner # " + creator);
        System.out.println("Id: " + id);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Magic number: " + magicNumber);
        System.out.println("Hash of the previous block:");
        System.out.println(prevHash);
        System.out.println("Hash of the block:");
        System.out.println(thisHash);
        if (messager == null) {
            System.out.println("Block data: no messages");
        } else {
            System.out.println("Block data: ");
            System.out.print(messager.toString());
        }
//        System.out.println("Block was generating for " + generationTime + " seconds");
    }
}
