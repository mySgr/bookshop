package bookshop.dao;

import bookshop.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> getBooks() throws Exception;

    List<Book> getBookRecom(String typeNo) throws Exception;

    List<Book> getBooks(String query, int pagenum, int pagesize) throws Exception;

    Book getBookinfo(String id) throws Exception;

    int getBookTotal() throws Exception;

    int updateBookState(String id, int state) throws Exception;

    Book addBookInfo(Book book) throws Exception;

    Book updateBookInfo(Book book, String id) throws Exception;
}
