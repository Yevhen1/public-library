package com.example.bookstorage.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Size(max = 30, message = "name must be between 1 and 30 characters")
    private String bookName;

    @Min(value = 1, message = "minimum number of pages 1")
    @Max(value = 1500, message = "maximum number of pages 1500")
    private int numberPages;

    @OneToOne(mappedBy = "bookId")
    private Bookmark bookmark;

    public Book(){
    }

    public Book(String bookName, int numberPages){
        this.bookName = bookName;
        this.numberPages = numberPages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    @Override
    public String toString(){
        return bookName + "-" + numberPages;
    }
}
