package main.repository;

import main.entity.BookTypes;
import main.entity.Books;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends CrudRepository<Books, Long> {
    Iterable<Books> findByType(BookTypes type);
}
