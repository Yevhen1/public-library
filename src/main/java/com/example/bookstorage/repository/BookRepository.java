package com.example.bookstorage.repository;

import com.example.bookstorage.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
