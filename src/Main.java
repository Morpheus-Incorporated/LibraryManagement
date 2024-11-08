public class Main {
    public static void main(String[] args) {
        LibraryManager libraryManager = new LibraryManager();

        // Adding books
        libraryManager.addBook("The Great Gatsby", "F. Scott Fitzgerald", 5);
        libraryManager.addBook("1984", "George Orwell", 3);

        // Display all books
        libraryManager.displayBooks();

        // Adding members
        libraryManager.addMember("John Doe", "123 Main St", "555-1234");
        libraryManager.addMember("Jane Smith", "456 Oak St", "555-5678");

        // Renting a book to a member
        libraryManager.rentBook(1, 1);  // Rent book with ID 1 to member with ID 1

        // Returning a book
        libraryManager.returnBook(1, 1);  // Return book with ID 1 from member with ID 1
    }
}
