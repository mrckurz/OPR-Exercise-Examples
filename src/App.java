import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class App {

    public static void main(String[] args) {

        List<Book> books = List.of(
            new Book("Clean Code",                 "Robert C. Martin",  464, 39.99, "IT"),
            new Book("Effective Java",             "Joshua Bloch",      412, 44.50, "IT"),
            new Book("The Pragmatic Programmer",   "Hunt & Thomas",     352, 36.00, "IT"),
            new Book("Der Process",                "Franz Kafka",       272,  9.90, "Roman"),
            new Book("Faust I",                    "J. W. Goethe",      210,  6.50, "Drama"),
            new Book("Sapiens",                    "Yuval N. Harari",   512, 24.99, "Sachbuch")
        );

        System.out.println("=== Manual implementation ===");
        run(new BookProcessorManual(books));

        System.out.println();
        System.out.println("=== Streams implementation ===");
        run(new BookProcessorStreams(books));
    }

    private static void run(BookProcessor processor) {

        // count() - keine Lambda, einfach Anzahl
        System.out.println("count             = " + processor.count());

        // Predicate<Book>: nimmt ein Book, liefert boolean
        Predicate<Book> isExpensive = b -> b.getPrice() > 20.0;
        System.out.println("# expensive books = " + processor.filter(isExpensive).size());

        // Methodenreferenz statt Lambda - aequivalent zu  b -> b.getPages()
        Comparator<Book> byPages = Comparator.comparingInt(Book::getPages);
        System.out.println("longest book      = " + processor.max(byPages).getTitle());
        System.out.println("shortest book     = " + processor.min(byPages).getTitle());

        // Function<Book, Long>: extrahiert eine Zahl pro Book
        Function<Book, Long> pages = b -> (long) b.getPages();
        System.out.printf ("mean pages        = %.1f%n", processor.mean(pages));

        // distinct ueber abgeleitetem String-Wert
        Function<Book, String> genre = Book::getGenre;
        System.out.println("# distinct genres = " + processor.countDistinctStrings(genre));

        // Consumer<Book>: Seiteneffekt pro Element (kein Rueckgabewert)
        System.out.println("titles:");
        processor.forEach(b -> System.out.println("  - " + b.getTitle()));
    }
}
