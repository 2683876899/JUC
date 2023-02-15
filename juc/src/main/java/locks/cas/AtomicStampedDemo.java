package locks.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicStampedReference;

@AllArgsConstructor
@NoArgsConstructor
@Data
class Book {
    private int id;
    private String bookName;
}

public class AtomicStampedDemo {

    public static void main(String[] args) {
        Book javaBook = new Book(1, "java");
        AtomicStampedReference<Book> book = new AtomicStampedReference<>(javaBook, 1);

        System.out.println(book.getReference() + "\t" + book.getStamp());
        Book mysqlBook = new Book(2, "mysql");
        boolean b;
        b = book.compareAndSet(javaBook, mysqlBook, book.getStamp(), book.getStamp() + 1);
        System.out.println(book.getReference() + "\t" + book.getStamp());
        b = book.compareAndSet(mysqlBook, javaBook, book.getStamp(), book.getStamp() + 1);

        System.out.println(book.getReference() + "\t" + book.getStamp());
    }
}
