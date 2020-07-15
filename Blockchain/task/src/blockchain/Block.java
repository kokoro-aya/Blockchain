package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Block implements Serializable {
    final private int id;
    private long timestamp;
    private long magicNumber;
    final private String prevHash;
    private String thisHash;
    private long generationTime;

    Block(int id, String prevHash, int zeros) {
        this.id = id + 1;
        this.prevHash = prevHash;
        long st = System.currentTimeMillis();
        long ts = new Date().getTime();
        long mn = new Random().nextLong();
        String hs = StringUtil.applySha256(id + " " + ts + " " + mn + " " + prevHash);;
        while (!StringUtil.isStartWithNZeros(hs, zeros)) {
            ts = new Date().getTime();
            mn = new Random().nextLong();
            hs = StringUtil.applySha256(id + " " + ts + " " + mn + " " + prevHash);
        }
        this.timestamp = ts;
        this.magicNumber = mn;
        this.thisHash = hs;
        long fi = System.currentTimeMillis();
        generationTime = (fi - st) / 1000;
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
        System.out.println("Id: " + id);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Magic number: " + magicNumber);
        System.out.println("Hash of the previous block:");
        System.out.println(prevHash);
        System.out.println("Hash of the block:");
        System.out.println(thisHash);
        System.out.println("Block was generating for " + generationTime + " seconds");
    }
}
