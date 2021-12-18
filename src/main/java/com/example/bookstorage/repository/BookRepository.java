package com.example.bookstorage.repository;

import com.example.bookstorage.models.Bookmark;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Bookmark, Integer> {
}
