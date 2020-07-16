package blockchain;

public class Message {
    final private String author;
    final private String content;

    public Message(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return author + ": " + content;
    }
}
