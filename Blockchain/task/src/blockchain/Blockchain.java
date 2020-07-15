package blockchain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Blockchain implements java.io.Serializable {
    final private int zeros;
    final private List<Block> chain;

    Blockchain(int zeros) {
        this.zeros = zeros;
        chain = new LinkedList<>();
    }

    void generate() {
        if (chain.isEmpty()) {
            chain.add(new Block(0, "0", zeros));
        } else {
            Block last = chain.get(chain.size() - 1);
            chain.add(new Block(last.getId(), last.getThisHash(), zeros));
        }
    }

    boolean validate() {
        Iterator<Block> it = chain.iterator();
        Block prev = null, next = it.next();
        while (it.hasNext()) {
            if (prev == null) {
                if (!next.getPrevHash().equals("0"))
                    return false;
            } else {
                if (!prev.getThisHash().equals(next.getPrevHash()))
                    return false;
            }
            prev = next;
            next = it.next();
        }
        return true;
    }

    void print() {
        for (var x: chain) {
            x.print();
            System.out.println();
        }
    }
}
