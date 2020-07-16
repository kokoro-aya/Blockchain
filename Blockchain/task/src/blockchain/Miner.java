package blockchain;

import java.io.*;
import java.util.concurrent.Callable;

public class Miner implements java.io.Serializable, Callable<Block> {
    private Blockchain blockchain;

    final private int id;
    private static int count;
    private boolean empty;

    private int zeros;
    private Messager messager;

    Miner(int zeros) {
        this.blockchain = new Blockchain();
        this.zeros = zeros;
        this.id = ++count;
        this.messager = null;
    }

    private Block generate() {
        Block newBlock;
        if (blockchain.isEmpty()) {
            newBlock = new Block(id, 0, "0", zeros, messager);
        } else {
            var oldBlock = blockchain.lastBlock();
            newBlock = new Block(id, oldBlock.getId(), oldBlock.getThisHash(), zeros, messager);
        }
        return newBlock;
    }

    void increaseDifficulty() {
        this.zeros += 1;
    }
    void decreaseDifficulty() {
        this.zeros -= 1;
    }

    boolean accept(Block b) {
        try {
            blockchain.append(b);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean serialize() {
        try (
                FileOutputStream fout = new FileOutputStream("blockchain-" + id + ".ser");
                ObjectOutputStream out = new ObjectOutputStream(fout);
        ) {
            if (blockchain.validate()) {
                out.writeObject(blockchain);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean deserialize() {
        try (
                FileInputStream fin = new FileInputStream("blockchain-" + id + ".ser");
                ObjectInputStream in = new ObjectInputStream(fin);
        ) {
            Blockchain b = (Blockchain) in.readObject();
            if (!b.validate()) {
                throw new Exception("Invalid blockchain!");
            } else {
                blockchain = b;
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    void printBlockchain() {
        blockchain.print();
    }

    @Override
    public Block call() throws Exception {
        return generate();
    }

    public void acceptMessage(Messager messager) {
        this.messager = messager;
    }
}
