package br.com.bdws.razzieawards.service;

import br.com.bdws.razzieawards.entity.Movie;
import br.com.bdws.razzieawards.viewobject.WinnerVO;
import br.com.bdws.razzieawards.viewobject.WinnerProducersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WinnerService {

    @Autowired
    private MovieService movieService;

    public WinnerProducersVO getWinnerProducers() {
        WinnerProducersVO winnerProducers = new WinnerProducersVO();
        for (Movie movie : movieService.findDistinctProducersAndYearWhereWinnerTrue()) {
            if (movie.getMovieYear() % 2 == 0) {
                winnerProducers.getMin().add(createWinner(movie));
            } else {
                winnerProducers.getMax().add(createWinner(movie));
            }
        }
        return winnerProducers;
    }

    private WinnerVO createWinner(Movie movie) {
        return WinnerVO.builder()
                .producers(movie.getProducers())
                .previousWin(movie.getMovieYear())
                .followingWin(movie.getMovieYear())
                .interval(3)
                .build();
    }
}