package blockchain;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter how many zeros the hash must start with:");
        Blockchain b = new Blockchain(sc.nextInt());

        try {
            FileOutputStream fout = new FileOutputStream("blockchain.ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            FileInputStream fin = new FileInputStream("blockchain.ser");
            ObjectInputStream in = new ObjectInputStream(fin);
            for (int i = 0; i < 5; i++) {
                b.generate();
                if (b.validate()) {
                    out.writeObject(b);
                }
                b = (Blockchain) in.readObject();
                if (!b.validate()) {
                    throw new Exception("Invalid blockchain!");
                }
            }
            out.close();
            in.close();
            fout.close();
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        b.print();
    }
}
