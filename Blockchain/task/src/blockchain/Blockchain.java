package blockchain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Blockchain implements java.io.Serializable {
    final private List<Block> chain;

    Blockchain() {
        chain = new LinkedList<>();
    }

    boolean isEmpty() {
        return chain.isEmpty();
    }

    Block lastBlock() {
        return chain.get(chain.size() - 1);
    }

    void append(Block b) throws Exception {
        chain.add(b);
        if (!validate())
            throw new Exception("Attempt to append a block invalidating the whole chain.");
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
