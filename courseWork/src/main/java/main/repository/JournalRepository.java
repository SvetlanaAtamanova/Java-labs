package main.repository;

import main.entity.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends CrudRepository<Journal, Long>{
    Iterable<Journal> findByBook(Books book);
    Iterable<Journal> findByClient(Clients clients);
}
