import java.sql.*;

public class LibraryManager {

    // Add a new book to the library
    public void addBook(String title, String author, int availableCopies) {
        String query = "INSERT INTO books (title, author, available_copies) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, availableCopies);
            stmt.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    // Display all books in the library
    public void displayBooks() {
        String query = "SELECT * FROM books";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("book_id") +
                        ", Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") +
                        ", Available Copies: " + rs.getInt("available_copies"));
            }
        } catch (SQLException e) {
            System.out.println("Error displaying books: " + e.getMessage());
        }
    }

    // Add a new member to the library
    public void addMember(String name, String address, String phoneNumber) {
        String query = "INSERT INTO members (name, address, phone_number) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phoneNumber);
            stmt.executeUpdate();
            System.out.println("Member added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding member: " + e.getMessage());
        }
    }

    // Rent a book to a member
    public void rentBook(int bookId, int memberId) {
        String checkAvailabilityQuery = "SELECT available_copies FROM books WHERE book_id = ?";
        String rentQuery = "INSERT INTO rentals (book_id, member_id, rental_date) VALUES (?, ?, CURDATE())";
        String updateBookQuery = "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkAvailabilityQuery);
             PreparedStatement rentStmt = conn.prepareStatement(rentQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateBookQuery)) {

            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt("available_copies") > 0) {
                rentStmt.setInt(1, bookId);
                rentStmt.setInt(2, memberId);
                rentStmt.executeUpdate();

                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();

                System.out.println("Book rented successfully!");
            } else {
                System.out.println("Book is not available.");
            }
        } catch (SQLException e) {
            System.out.println("Error renting book: " + e.getMessage());
        }
    }

    // Return a book to the library
    public void returnBook(int bookId, int memberId) {
        String returnQuery = "UPDATE rentals SET return_date = CURDATE() WHERE book_id = ? AND member_id = ? AND return_date IS NULL";
        String updateBookQuery = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement returnStmt = conn.prepareStatement(returnQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateBookQuery)) {

            returnStmt.setInt(1, bookId);
            returnStmt.setInt(2, memberId);
            int rowsUpdated = returnStmt.executeUpdate();
            if (rowsUpdated > 0) {
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("No active rental found for the book.");
            }
        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }
}
