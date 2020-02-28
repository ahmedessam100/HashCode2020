public class Book {
    int id;
    int reward;

    public Book(int id, int reward) {
        this.id = id;
        this.reward = reward;
    }

    @Override
    public String toString() {
        return id + "";
    }
}
