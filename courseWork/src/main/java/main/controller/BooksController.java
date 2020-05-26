package main.controller;
import main.entity.BookTypes;
import main.entity.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import main.repository.*;
import main.exceptoin.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/api/books", produces = "application/json")
public class BooksController {

    private BooksRepository booksRepository;
    private BookTypesRepository bookTypesRepository;

    @Autowired
    public BooksController(
            BooksRepository booksRepository,
            BookTypesRepository bookTypesRepository
    ) {
        this.booksRepository = booksRepository;
        this.bookTypesRepository = bookTypesRepository;
    }

    @GetMapping
    public Iterable<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable long id) {
        return booksRepository.findById(id).map(books -> new ResponseEntity<>(books, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/bookTypes/{id}")
    public Iterable<Books> getTypeId(@PathVariable long id) {
        BookTypes type = bookTypesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return booksRepository.findByType(type);
    }


    @PostMapping
    public Books createBook(@Valid @RequestBody Books book) {
        return booksRepository.save(book);
    }

    @PutMapping("/{id}")
    public void updateBook(
            @PathVariable long id,
            @Valid @RequestBody Books book
    ) {
        if (id != book.getId()) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }
        booksRepository.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable long id) {
        try {
            booksRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
            throw new EntityNotFoundException(id);
        }
    }

}

