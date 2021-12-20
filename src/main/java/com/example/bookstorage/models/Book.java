package com.example.bookstorage.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String bookName;

    private int numberPages;

//    @OneToMany(mappedBy = "bookId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "bookmark_list")
//    private List<Bookmark> bookmarkList = new ArrayList<>();
    @OneToOne(mappedBy = "bookId")
    private Bookmark bookmark;

//    @OneToOne(mappedBy = "address")
//    private User user;

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

//    public List<Bookmark> getBookmarkList() {
//        return bookmarkList;
//    }
//
//    public void setBookmarkList(List<Bookmark> bookmarkList) {
//        this.bookmarkList = bookmarkList;
//    }


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
