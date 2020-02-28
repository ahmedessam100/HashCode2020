import java.util.*;

public class Library {
    int id;
    int signUpProcessDays;
    int shipCapacityPerDay;
    List<Book> books;
    Queue<Book> scannedBooks = new ArrayDeque<>();

    public Library(int id,int signUpProcessDays, int shipCapacityPerDay) {
        this.id = id;
        this.signUpProcessDays = signUpProcessDays;
        this.shipCapacityPerDay = shipCapacityPerDay;
        books = new ArrayList<>();
    }

    public void addBooks(Book[] books){
        this.books = Arrays.asList(books);
        this.books.sort((b1,b2)->b2.reward - b1.reward);
        this.books = new LinkedList<>(this.books);
    }

    public float getRatioInDays(int days) {
        if (days <= 0 )
            return Integer.MIN_VALUE;

        int numBooksToShip = shipCapacityPerDay * days;
        int rewards = 0;
        var iterator = books.iterator();
        while (iterator.hasNext() && numBooksToShip !=0){
            rewards +=iterator.next().reward;
            numBooksToShip--;
        }

        return ((float)rewards) /signUpProcessDays;
    }

    public void scan(Set<Book> scanned){
        int shipped = 0;
        var iterator = books.iterator();
        while (iterator.hasNext() && shipped !=shipCapacityPerDay){
            Book book = iterator.next();
            if (!scanned.contains(book)){
                scanned.add(book);
                scannedBooks.add(book);
                shipped++;
                iterator.remove();
            }
        }

    }
}
