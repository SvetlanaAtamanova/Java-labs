package main.controller;
import main.entity.BookTypes;
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
@RequestMapping(path = "/api/bookTypes", produces = "application/json")
public class BookTypesController {

    private BookTypesRepository bookTypesRepository;

    @Autowired
    public BookTypesController(
            BookTypesRepository bookTypesRepository
    ) {
        this.bookTypesRepository = bookTypesRepository;
    }

    @GetMapping
    public Iterable<BookTypes> getAllTypes() {
        return bookTypesRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookTypes> getTypeById(@PathVariable long id) {
        return bookTypesRepository.findById(id).map(types -> new ResponseEntity<>(types, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public BookTypes createType(@Valid @RequestBody BookTypes type) {
        return bookTypesRepository.save(type);
    }

    @PutMapping("/{id}")
    public void updateType(
            @PathVariable long id,
            @Valid @RequestBody BookTypes type
    ) {
        if (id != type.getId()) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }
        bookTypesRepository.save(type);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteType(@PathVariable long id) {
        try {
            bookTypesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
            throw new EntityNotFoundException(id);
        }    }

}

