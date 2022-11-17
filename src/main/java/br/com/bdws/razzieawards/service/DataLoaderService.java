package br.com.bdws.razzieawards.service;

import br.com.bdws.razzieawards.entity.Movie;
import br.com.bdws.razzieawards.entity.MovieProducer;
import br.com.bdws.razzieawards.entity.Producer;
import br.com.bdws.razzieawards.util.CSVHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoaderService {

    private final MovieService movieService;
    private final ProducerService producerService;

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
        List<Producer> savedProducers = producerService.saveAll(createProducers(csvRecords));
        movieService.saveAll(createMovies(csvRecords, savedProducers));
        log.info("Loading movies finished.");
    }

    private List<Movie> createMovies(List<CSVRecord> csvRecords, List<Producer> savedProducers) {
        return csvRecords.stream().map(csv -> createMovie(csv, savedProducers)).collect(Collectors.toList());
    }

    private Movie createMovie(CSVRecord csv, List<Producer> savedProducers) {
        if (csv.size() < 5) {
            log.error("Wrong number of columns in csv file row: " + System.lineSeparator() + csv);
        }
        Movie movie = Movie.builder()
                .movieYear(Integer.parseInt(csv.get(0)))
                .title(csv.get(1))
                .studios(csv.get(2))
                .winner(csv.get(4).equalsIgnoreCase("yes"))
                .build();
        movie.setProducers(filterProducersAndCreateMovieProducers(movie, csv.get(3), savedProducers));
        return movie;
    }

    private List<MovieProducer> filterProducersAndCreateMovieProducers(Movie movie, String producers, List<Producer> savedProducers) {
        List<MovieProducer> movieProducers = new ArrayList<>();
        for (String prod : splitProducers(producers)) {
            Producer producer = savedProducers.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(prod))
                    .findFirst()
                    .orElse(null);
            if (producer != null) {
                movieProducers.add(new MovieProducer(movie, producer));
            }
        }
        return movieProducers;
    }

    private List<Producer> createProducers(List<CSVRecord> csvRecords) {
        Set<String> uniqueNameProducers = new HashSet<>();
        csvRecords.forEach(csv -> {
            uniqueNameProducers.addAll(splitProducers(csv.get(3)));
        });
        return uniqueNameProducers.stream()
                .map(n -> Producer.builder().name(n).build())
                .collect(Collectors.toList());
    }

    private List<String> splitProducers(String producers) {
        String[] splitedProducers = producers.split("(,)|( and ?)");
        return Arrays.stream(splitedProducers)
                .map(p -> p.trim())
                .filter(p -> p.length() > 0)
                .collect(Collectors.toList());
    }
}