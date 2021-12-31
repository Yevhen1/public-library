package com.example.bookstorage.models;

import javax.persistence.*;

@Entity
@Table
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int bookmarkPage;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "reader_id")
    private Reader readerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book bookId;

    public Bookmark(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookmarkPage() {
        return bookmarkPage;
    }

    public void setBookmarkPage(int bookmarkPage) {
        this.bookmarkPage = bookmarkPage;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public Reader getReaderId() {
        return readerId;
    }

    public void setReaderId(Reader readerId) {
        this.readerId = readerId;
    }

    @Override
    public String toString(){
//        return bookmarkPage + bookId.getBookName() + bookId.getNumberPages() + readerId.getReaderName();
        return String.valueOf(bookmarkPage);
    }
}
