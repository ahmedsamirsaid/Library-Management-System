package com.example.library.services;

import com.example.library.entities.Patron;
import com.example.library.errorHandeling.ResourceNotFoundException;
import com.example.library.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "patrons")
public class PatronService implements IPatronService {

    @Autowired
    private PatronRepository patronRepository;


    @Override
    @Cacheable
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    @Cacheable(key = "#id")
    public Patron getPatronById(Long id) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        return optionalPatron.orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
    }

    @Override
    @CacheEvict(allEntries = true)
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    @Transactional
    @CachePut(key = "#id")
    public Patron updatePatron(Long id, Patron patron) {
        Patron existingPatron = getPatronById(id);
        existingPatron.setName(patron.getName());
        existingPatron.setEmail(patron.getEmail());
        existingPatron.setPhone(patron.getPhone());
        existingPatron.setAddress(patron.getAddress());
        return patronRepository.save(existingPatron);
    }

    @Override
    @CacheEvict
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
