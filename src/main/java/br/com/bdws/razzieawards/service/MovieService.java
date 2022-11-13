package br.com.bdws.razzieawards.service;

import br.com.bdws.razzieawards.entity.Movie;
import br.com.bdws.razzieawards.repository.IMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    @Autowired
    private IMovieRepository repository;

    public List<Movie> saveAll(List<Movie> movies) {
        return repository.saveAll(movies);
    }

    public List<Movie> findDistinctProducersAndYearWhereWinnerTrue() {
        return repository.findDistinctProducersAndYearWhereWinnerTrue();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}