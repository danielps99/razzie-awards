package br.com.bdws.razzieawards.winner;

import br.com.bdws.razzieawards.service.DataLoaderService;
import br.com.bdws.razzieawards.service.MovieService;
import br.com.bdws.razzieawards.viewobject.WinnerProducersVO;
import br.com.bdws.razzieawards.winner.config.BaseControlerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class WinnerControllerTest extends BaseControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataLoaderService dataLoaderService;

    @Autowired
    MovieService movieService;

    private StringBuilder sbMockCsvFileMovies;

    private void addMovie(String movie) {
        sbMockCsvFileMovies.append(movie).append(System.lineSeparator());
    }

    private void loadMoviesFromMockCsv() {
        try {
            InputStream inputStreamCsv = new ByteArrayInputStream(sbMockCsvFileMovies.toString().getBytes());
            dataLoaderService.loadMoviesFromCsvInputStream(inputStreamCsv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void beforeEach() {
        sbMockCsvFileMovies  = new StringBuilder("year;title;studios;producers;winner")
                .append(System.lineSeparator());
    }

    @AfterEach
    public void afterAll() {
        movieService.deleteAll();
    }

    @Test
    void shouldThrowExceptionWhenCvsLineOnlyFourColumns() {
        ArrayIndexOutOfBoundsException thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            addMovie("1980;Windows;United Artists;Mike Lobell");
            loadMoviesFromMockCsv();
        });
        assertEquals("Index 4 out of bounds for length 4", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCvsLineOnlyThreeColumns() {
        ArrayIndexOutOfBoundsException thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            addMovie("1980;Windows;United Artists");
            loadMoviesFromMockCsv();
        });
        assertEquals("Index 3 out of bounds for length 3", thrown.getMessage());
    }

    @Test
    void shouldReturnEmptyMinAndEmptyMaxWhenWhenThereAreNoMovie() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertNotNull(mvcResult);
        assertTrue( result.getMin().isEmpty());
        assertTrue( result.getMax().isEmpty());
    }

    @Test
    void onlyToTestLoadData() throws Exception {

        addMovie("1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes");
        addMovie("1981;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes");

        loadMoviesFromMockCsv();

        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertNotNull(mvcResult);
        assertEquals(1, result.getMin().size());
        assertEquals(1, result.getMax().size());
    }
}