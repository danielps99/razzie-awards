package br.com.bdws.razzieawards.service;

import br.com.bdws.razzieawards.entity.Movie;
import br.com.bdws.razzieawards.repository.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private IMovieRepository repository;

    public List<Movie> saveAll(List<Movie> movies) {
        return repository.saveAll(movies);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}