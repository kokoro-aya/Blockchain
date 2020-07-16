package blockchain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Messager {
    private List<Message> messages;

    Messager() {
        messages = new LinkedList<>();
    }

    void add(Message mes) {
        messages.add(mes);
    }

    void add(Collection<Message> mess) {
        messages.addAll(mess);
    }

    boolean hasNext() {
        return messages.isEmpty();
    }

    Message next() {
        Message mes = messages.get(0);
        messages.remove(0);
        return mes;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < messages.size(); i++) {
            s += messages.get(i).toString() + "\n";
        }
        return s;
    }
}
