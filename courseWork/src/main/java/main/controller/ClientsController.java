package main.controller;

import main.entity.Clients;
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
@RequestMapping(path = "/api/clients", produces = "application/json")
public class ClientsController {

    private ClientsRepository clientsRepository;

    @Autowired
    public ClientsController(
            ClientsRepository clientsRepository
    ) {
        this.clientsRepository = clientsRepository;
    }

    @GetMapping
    public Iterable<Clients> getAllClients() {
        return clientsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clients> getClientById(@PathVariable long id) {
        return clientsRepository.findById(id).map(clients -> new ResponseEntity<>(clients, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public Clients createClient(@Valid @RequestBody Clients clients) {
        return clientsRepository.save(clients);
    }

    @PutMapping("/{id}")
    public void updateClient(
            @PathVariable long id,
            @Valid @RequestBody Clients clients
    ) {
        if (id != clients.getId()) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }
        clientsRepository.save(clients);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable long id) {
        try {
            clientsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
            throw new EntityNotFoundException(id);
        }
    }

}


