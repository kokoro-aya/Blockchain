package blockchain;

public class Main {
    public static void main(String[] args) {
        Blockchain b = new Blockchain();
        b.generate();
        b.generate();
        b.generate();
        b.generate();
        b.generate();
//        System.out.println(b.validate());
        b.print();
    }
}
