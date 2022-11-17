package br.com.bdws.razzieawards.service;

import br.com.bdws.razzieawards.entity.Producer;
import br.com.bdws.razzieawards.repository.IProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    @Autowired
    private IProducerRepository repository;

    public List<Producer> saveAll(List<Producer> producers) {
        return repository.saveAll(producers);
    }
}