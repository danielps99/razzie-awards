package br.com.bdws.razzieawards.repository;

import br.com.bdws.razzieawards.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "SELECT distinct new Movie(m.producers, m.movieYear) FROM Movie m WHERE winner=true")
    List<Movie> findDistinctProducersAndYearWhereWinnerTrue();
}