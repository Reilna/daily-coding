public class Main {
    public static void main(String[] args) {
        LibraryItem[] library = new LibraryItem[3];
        library[0] = new Book("Властелин Колец", "Дж. Р. Р. Толкин");
        library[1] = new Dvd("Интерстеллар", 169);
        library[2] = new Book("1984", "Джордж Оруэлл");

        for(LibraryItem item : library) {
            item.printInfo();
        }

    }
}

enum ItemStatus {
    AVAILABLE, BORROWED
}


abstract class LibraryItem{
    private String title;
    private ItemStatus status;

    LibraryItem(String title) {
        this.title = title;
        status = ItemStatus.AVAILABLE;
    }

    String getTitle() {
        return title;
    }

    void borrow() {
        if (status == ItemStatus.AVAILABLE) {
            status = ItemStatus.BORROWED;
        }
    }

    void returnItem() {
        if (status == ItemStatus.BORROWED) {
            status = ItemStatus.AVAILABLE;
        }
    }

    boolean isAvailable() {
        return status == ItemStatus.AVAILABLE;
    }

    abstract void printInfo();

}

class Book extends LibraryItem {

    private String author;

    Book(String title, String author) {
        super(title);
        this.author = author;
    }

    @Override
    void printInfo() {
        System.out.println(getTitle() + " " + author + " " + isAvailable());
    }
}

class Dvd extends  LibraryItem {
    private int durationMinutes;

    Dvd(String title, int durationMinutes) {
        super(title);
        this.durationMinutes = durationMinutes;
    }

    @Override
    void printInfo() {
        System.out.println(durationMinutes + " " + getTitle() + " " + isAvailable());
    }
}