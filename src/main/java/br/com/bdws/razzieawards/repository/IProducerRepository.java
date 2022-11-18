package br.com.bdws.razzieawards.repository;

import br.com.bdws.razzieawards.entity.Producer;
import br.com.bdws.razzieawards.viewobject.ProducerMovieYearVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProducerRepository extends JpaRepository<Producer, Long> {

    @Query(value = "SELECT DISTINCT new br.com.bdws.razzieawards.viewobject.ProducerMovieYearVO(p.name, m.movieYear) FROM MovieProducer mp join mp.producer p join mp.movie m where m.winner=true")
    List<ProducerMovieYearVO> findDistinctProducerAndYearWhereWinnerTrue();
}