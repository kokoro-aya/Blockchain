package blockchain;

import java.util.Date;

public class Block {
    private int id;
    private long timestamp;
    private String prevHash;
    private String thisHash;

    Block(int id, String prevHash) {
        this.id = id + 1;
        this.prevHash = prevHash;
        this.timestamp = new Date().getTime();
        this.thisHash = StringUtil.applySha256(id + " " + timestamp + " " + prevHash);
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
        System.out.println("Hash of the previous block:");
        System.out.println(prevHash);
        System.out.println("Hash of the block:");
        System.out.println(thisHash);
    }
}
