package br.com.bdws.razzieawards.service;

import br.com.bdws.razzieawards.entity.Movie;
import br.com.bdws.razzieawards.util.CSVHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoaderService {

    private final MovieService movieService;

    @Value("${fileMoviesCsv}")
    private String fileMoviesCsv;

    public void loadMoviesFromCsv() {
        log.info("Started load movies...");

        URL resource = DataLoaderService.class.getClassLoader().getResource(fileMoviesCsv);
        if (resource == null) {
            throw new IllegalArgumentException("File not found: "+ fileMoviesCsv);
        }

        try {
            InputStream targetStream = new FileInputStream(new File(resource.toURI()));
            log.info("Reading file: "+ fileMoviesCsv);
            List<CSVRecord> csvRecords = CSVHelper.getCsvRecords(targetStream);
            List<Movie> movies = csvRecords.stream().map(csv -> createMovie(csv)).collect(Collectors.toList());
            movieService.saveAll(movies);
            log.info("Loading movies finished.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static Movie createMovie(CSVRecord csv) {
        if (csv.size() < 5) {
            log.error("Wrong number of columns in csv file row: " + System.lineSeparator() + csv);
        }
        return Movie.builder()
                .movieYear(Integer.parseInt(csv.get(0)))
                .title(csv.get(1))
                .studios(csv.get(2))
                .producers(csv.get(3))
                .winner(csv.get(4).equalsIgnoreCase("yes"))
                .build();
    }
}