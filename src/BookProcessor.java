import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface BookProcessor {

    long count();

    List<Book> filter(Predicate<Book> predicate);

    Book max(Comparator<Book> comparator);

    Book min(Comparator<Book> comparator);

    double mean(Function<Book, Long> function);

    long countDistinctStrings(Function<Book, String> function);

    void forEach(Consumer<Book> consumer);
}
