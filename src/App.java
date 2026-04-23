public class App {
    public static void main(String[] args) {
        // Pair<Integer> -- Comparable ist von Integer bereits implementiert
        Pair<Integer> numbers = new Pair<>(42, 5);
        System.out.println(numbers);
        System.out.println("min   = " + numbers.min());
        System.out.println("max   = " + numbers.max());
        System.out.println("equal = " + numbers.isEqual());
        System.out.println();

        // Pair<String> -- String ist ebenfalls Comparable (lexikografisch)
        Pair<String> words = new Pair<>("Hagenberg", "Linz");
        System.out.println(words);
        System.out.println("min (lex.) = " + words.min());
        System.out.println("max (lex.) = " + words.max());
        System.out.println();

        // Pair<Person> -- unsere eigene Comparable-Klasse (Vergleich nach Alter)
        Pair<Person> people = new Pair<>(
            new Person("Alice", 29),
            new Person("Bob", 34)
        );
        System.out.println(people);
        System.out.println("younger = " + people.min());
        System.out.println("older   = " + people.max());
        System.out.println();

        // Innere Klasse Stats verwendet den generischen Typ T der aeusseren Klasse
        Pair<Integer>.Stats stats = numbers.getStats();
        System.out.println("stats.min   = " + stats.getMinimum());
        System.out.println("stats.max   = " + stats.getMaximum());
        System.out.println("stats.equal = " + stats.isEqual());
        System.out.println();

        // swap zeigt, dass first/second veraendert werden koennen
        numbers.swap();
        System.out.println("after swap: " + numbers);
    }
}
