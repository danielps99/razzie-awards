package br.com.bdws.razzieawards.service;

import br.com.bdws.razzieawards.entity.Movie;
import br.com.bdws.razzieawards.util.CSVHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
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

    public void decideIfShouldLoadMoviesFromCsvFile() {
        if ("".equalsIgnoreCase(fileMoviesCsv)) {
            return;
        }
        loadMoviesFromCsvFile();
        log.info("Try access http://127.0.0.1:8080/api/winner/producers-minimum-maximum-interval");
    }

    public void loadMoviesFromCsvFile() {
        log.info("Reading file: "+ fileMoviesCsv);
        try {
            URL resource = DataLoaderService.class.getClassLoader().getResource(fileMoviesCsv);
            InputStream inputStreamCsv = new FileInputStream(new File(resource.toURI()));
            loadMoviesFromCsvInputStream(inputStreamCsv);
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File not found: "+ fileMoviesCsv);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void loadMoviesFromCsvInputStream(InputStream inputStreamCsv) throws IOException {
        log.info("Started load movies...");
        List<CSVRecord> csvRecords = CSVHelper.getCsvRecords(inputStreamCsv);
        List<Movie> movies = csvRecords.stream().map(csv -> createMovie(csv)).collect(Collectors.toList());
        movieService.saveAll(movies);
        log.info("Loading movies finished.");
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