public class Book {

    private String title;
    private String author;
    private int pages;
    private double price;
    private String genre;

    public Book(String title, String author, int pages, double price, String genre) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.price = price;
        this.genre = genre;
    }

    public String getTitle()  { return title; }
    public String getAuthor() { return author; }
    public int    getPages()  { return pages; }
    public double getPrice()  { return price; }
    public String getGenre()  { return genre; }

    @Override
    public String toString() {
        return String.format("Book[%s by %s, %d p, %.2f EUR, %s]",
                title, author, pages, price, genre);
    }
}
