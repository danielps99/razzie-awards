package br.com.bdws.razzieawards.service;

import br.com.bdws.razzieawards.entity.Movie;
import br.com.bdws.razzieawards.viewobject.WinnerProducersVO;
import br.com.bdws.razzieawards.viewobject.WinnerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WinnerService {

    @Autowired
    private MovieService movieService;

    public WinnerProducersVO getWinnerProducers() {
        List<Movie> winnerMovies = movieService.findDistinctProducersAndYearWhereWinnerTrue();

        List<Map.Entry<String, List<Movie>>> groupedMovies = getGroupedMoviesByProducersThatWinnerMoreThanOnce(winnerMovies);

        List <WinnerVO> winnersMinInterval = new ArrayList<>();
        List <WinnerVO> winnersMaxInterval = new ArrayList<>();
        fillMinimumAndMaximumIntervalOfPossibleWinners(groupedMovies, winnersMinInterval, winnersMaxInterval);

        return filterWinnersAndCreateWinnerProducersVO(winnersMinInterval, winnersMaxInterval);
    }

    private WinnerProducersVO filterWinnersAndCreateWinnerProducersVO(List<WinnerVO> winnersMinInterval, List<WinnerVO> winnersMaxInterval) {
        Integer minInterval = getMinimumIntervalAcrossTheWinners(winnersMinInterval);
        Integer maxInterval = getMaximumIntervalAcrossTheWinners(winnersMaxInterval);

        List<WinnerVO> minimums = filterWinnersByInterval(winnersMinInterval, minInterval);
        List<WinnerVO> maximus = filterWinnersByInterval(winnersMaxInterval, maxInterval);

        minimums.sort(Comparator.comparing(WinnerVO::getPreviousWin));
        maximus.sort(Comparator.comparing(WinnerVO::getPreviousWin));
        return new WinnerProducersVO(minimums, maximus);
    }

    private Integer getMaximumIntervalAcrossTheWinners(List<WinnerVO> winnersMaxInterval) {
        if (winnersMaxInterval.isEmpty()) {
            return null;
        }
        return Collections.max(getIntervalsAcrossTheWinners(winnersMaxInterval));
    }

    private Integer getMinimumIntervalAcrossTheWinners(List<WinnerVO> winnersMinInterval) {
        if (winnersMinInterval.isEmpty()) {
            return null;
        }
        return Collections.min(getIntervalsAcrossTheWinners(winnersMinInterval));
    }

    private List<Integer> getIntervalsAcrossTheWinners(List<WinnerVO> winners) {
        if (winners.isEmpty()) {
            return new ArrayList<>();
        }
        return winners.stream().map(WinnerVO::getInterval).collect(Collectors.toList());
    }

    private List<WinnerVO> filterWinnersByInterval(List<WinnerVO> winners, Integer interval) {
        if (interval == null || winners.isEmpty()) {
            return new ArrayList<>();
        }
        return winners.stream().filter(w -> w.getInterval() == interval).collect(Collectors.toList());
    }

    private void fillMinimumAndMaximumIntervalOfPossibleWinners(List<Map.Entry<String, List<Movie>>> groupedMovies, List <WinnerVO> mins, List <WinnerVO> maxs) {
        for (Map.Entry<String, List<Movie>> group : groupedMovies) {
            List<Movie> movies = group.getValue();
            movies.sort(Comparator.comparing(Movie::getMovieYear));

            mins.add(getPossibleWinnerWithMaximumOrMinimumInterval(movies, 0, false));
            maxs.add(getPossibleWinnerWithMaximumOrMinimumInterval(movies, 0, true));
        }
    }

    private WinnerVO getPossibleWinnerWithMaximumOrMinimumInterval(List<Movie> movies, int index, boolean getMaximum) {
        Movie first = movies.get(index);
        WinnerVO created = WinnerVO.builder().producers(first.getProducers()).build();
        if (index < movies.size() -1) {
            index++;
            Movie last = movies.get(index);
            created.setPreviousWin(first.getMovieYear())
                    .setFollowingWin(last.getMovieYear());

            WinnerVO returned = getPossibleWinnerWithMaximumOrMinimumInterval(movies, index, getMaximum);

            created.setPreviousWin(first.getMovieYear())
                    .setFollowingWin(last.getMovieYear());

            created = selectPossibleWinnerWithMaximumOrMinimumInterval(returned, created, getMaximum);
        }
        return created;
    }

    private WinnerVO selectPossibleWinnerWithMaximumOrMinimumInterval(WinnerVO w1, WinnerVO w2, boolean getMaximum) {
        if (getMaximum) {
            return w1.hasInterval() && w1.getInterval() > w2.getInterval() ? w1 : w2;
        }
        return w1.hasInterval() && w1.getInterval() < w2.getInterval() ? w1 : w2;
    }

    private List<Map.Entry<String, List<Movie>>> getGroupedMoviesByProducersThatWinnerMoreThanOnce(List<Movie> winnerMovies) {
        Map<String, List<Movie>> groupedMovies = groupMoviesByProducers(winnerMovies);
        return groupedMovies.entrySet().stream().filter(map -> map.getValue().size() > 1).collect(Collectors.toList());
    }

    private Map<String, List<Movie>> groupMoviesByProducers(List<Movie> winnerMovies) {
        return winnerMovies.stream().collect(Collectors.groupingBy(Movie::getProducers));
    }
}