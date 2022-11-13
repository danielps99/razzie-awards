package br.com.bdws.razzieawards.winner;

import br.com.bdws.razzieawards.service.DataLoaderService;
import br.com.bdws.razzieawards.service.MovieService;
import br.com.bdws.razzieawards.viewobject.WinnerProducersVO;
import br.com.bdws.razzieawards.viewobject.WinnerVO;
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
    private MovieService movieService;

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

    private void assertWinnerVoEquals(WinnerVO vo, String producers, Integer interval, Integer previous, Integer following) {
        assertEquals(producers, vo.getProducers());
        assertEquals(interval, vo.getInterval());
        assertEquals(previous, vo.getPreviousWin());
        assertEquals(following, vo.getFollowingWin());
    }

    private void addAndLoadSomeMoviesFromMockCsv() {
        addMovie("1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes");
        addMovie("1980;Cruising;Lorimar Productions, United Artists;Jerry Weintraub;");
        addMovie("1980;The Formula;MGM, United Artists;Steve Shagan;");
        addMovie("1980;Friday the 13th;Paramount Pictures;Sean S. Cunningham;");
        addMovie("1980;The Nude Bomb;Universal Studios;Jennings Lang;");
        addMovie("1980;The Jazz Singer;Associated Film Distribution;Jerry Leider;");
        addMovie("1980;Raise the Titanic;Associated Film Distribution;William Frye;");
        addMovie("1980;Saturn 3;Associated Film Distribution;Stanley Donen;");
        addMovie("1980;Windows;United Artists;Mike Lobell;");
        addMovie("1980;Xanadu;Universal Studios;Lawrence Gordon;");
        addMovie("1981;Mommie Dearest;Paramount Pictures;Frank Yablans;yes");
        addMovie("1981;Endless Love;Universal Studios, PolyGram;Dyson Lovell;");
        addMovie("1981;Heaven's Gate;United Artists;Joann Carelli;");
        addMovie("1981;The Legend of the Lone Ranger;Universal Studios, Associated Film Distribution;Walter Coblenz;");
        addMovie("1981;Tarzan, the Ape Man;MGM, United Artists;John Derek;");
        addMovie("1982;Inchon;MGM;Mitsuharu Ishii;yes");
        addMovie("1982;Annie;Columbia Pictures;Ray Stark;");
        addMovie("1982;Butterfly;Analysis Film Releasing;Matt Cimber;");
        addMovie("1982;Megaforce;20th Century Fox;Albert S. Ruddy;");
        addMovie("1982;The Pirate Movie;20th Century Fox;David Joseph;");
        addMovie("1983;The Lonely Lady;Universal Studios;Robert R. Weston;yes");
        addMovie("1983;Hercules;MGM, United Artists, Cannon Films;Yoram Globus and Menahem Golan;");
        addMovie("1983;Jaws 3-D;Universal Studios;Rupert Hitzig;");
        addMovie("1983;Stroker Ace;Warner Bros., Universal Studios;Hank Moonjean;");
        addMovie("1983;Two of a Kind;20th Century Fox;Roger M. Rothstein and Joe Wizan;");
        addMovie("1984;Bolero;Cannon Films;Bo Derek;yes");
        addMovie("1984;Cannonball Run II;Warner Bros.;Albert S. Ruddy;");
        addMovie("1984;Rhinestone;20th Century Fox;Marvin Worth and Howard Smith;");
        addMovie("1984;Sheena;Columbia Pictures;Paul Aratow;");
        addMovie("1984;Where the Boys Are '84;TriStar Pictures;Allan Carr;");
        addMovie("1985;Rambo: First Blood Part II;Columbia Pictures;Buzz Feitshans;yes");
        addMovie("1985;Fever Pitch;MGM, United Artists;Freddie Fields;");
        addMovie("1985;Revolution;Warner Bros.;Irwin Winkler;");
        addMovie("1985;Rocky IV;MGM, United Artists;Irwin Winkler and Robert Chartoff;");
        addMovie("1985;Year of the Dragon;MGM, United Artists;Dino De Laurentiis;");
        addMovie("1986;Howard the Duck;Universal Studios;Gloria Katz;yes");
        addMovie("1986;Under the Cherry Moon;Warner Bros.;Bob Cavallo, Joe Ruffalo and Steve Fargnoli;yes");
        addMovie("1986;Blue City;Paramount Pictures;William L. Hayward and Walter Hill;");
        addMovie("1986;Cobra;Warner Bros., Cannon Films;Yoram Globus and Menahem Golan;");
        addMovie("1986;Shanghai Surprise;MGM;John Kohn;");
        addMovie("1987;Leonard Part 6;Columbia Pictures;Bill Cosby;yes");
        addMovie("1987;Ishtar;Columbia Pictures;Warren Beatty;");
        addMovie("1987;Jaws: The Revenge;Universal Studios;Joseph Sargent;");
        addMovie("1987;Tough Guys Don't Dance;Cannon Films;Yoram Globus and Menahem Golan;");
        addMovie("1987;Who's That Girl;Warner Bros.;Rosilyn Heller and Bernard Williams;");
        addMovie("1988;Cocktail;Touchstone Pictures;Ted Field and Robert W. Cort;yes");
        addMovie("1988;Caddyshack II;Warner Bros.;Neil Canton, Jon Peters and Peter Guber;");
        addMovie("1988;Hot to Trot;Warner Bros.;Steve Tisch;");
        addMovie("1988;Mac and Me;Orion Pictures;R. J. Louis;");
        addMovie("1988;Rambo III;TriStar Pictures, Carolco Pictures;Buzz Feitshans;");
        addMovie("1989;Star Trek V: The Final Frontier;Paramount Pictures;Harve Bennett;yes");
        addMovie("1989;The Karate Kid Part III;Columbia Pictures;Jerry Weintraub;");
        addMovie("1989;Lock Up;TriStar Pictures, Carolco Pictures;Charles Gordon and Lawrence Gordon;");
        addMovie("1989;Road House;United Artists;Joel Silver;");
        addMovie("1989;Speed Zone;Orion Pictures;Murray Shostack;");
        addMovie("1990;The Adventures of Ford Fairlane;20th Century Fox;Steven Perry and Joel Silver;yes");
        addMovie("1990;Ghosts Can't Do It;Triumph Releasing;Bo Derek;yes");
        addMovie("1990;The Bonfire of the Vanities;Warner Bros.;Brian De Palma;");
        addMovie("1990;Graffiti Bridge;Warner Bros.;Randy Phillips and Craig Rice;");
        addMovie("1990;Rocky V;United Artists;Robert Chartoff and Irwin Winkler;");
        loadMoviesFromMockCsv();
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
    void shouldThrowExceptionWhenCvsLineHasOnlyFourColumns() {
        ArrayIndexOutOfBoundsException thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            addMovie("1980;Windows;United Artists;Mike Lobell");
            loadMoviesFromMockCsv();
        });
        assertEquals("Index 4 out of bounds for length 4", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCvsLineHasOnlyThreeColumns() {
        ArrayIndexOutOfBoundsException thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            addMovie("1980;Windows;United Artists");
            loadMoviesFromMockCsv();
        });
        assertEquals("Index 3 out of bounds for length 3", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCvsLineHasStringOnYear() {
        NumberFormatException thrown = Assertions.assertThrows(NumberFormatException.class, () -> {
            addMovie("some year;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes");
            loadMoviesFromMockCsv();
        });
        assertEquals("For input string: \"some year\"", thrown.getMessage());
    }

    @Test
    void shouldReturnEmptyMinAndMaxWhenThereAreNoMovie() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertNotNull(mvcResult);
        assertTrue(result.getMin().isEmpty());
        assertTrue(result.getMax().isEmpty());
    }

    @Test
    void shouldReturnEmptyMinAndMaxWhenProducersWonOnlyOnce() throws Exception {
        addMovie("1991;Hudson Hawk;TriStar Pictures;Joel Silver;yes");
        addMovie("1992;Shining Through;20th Century Fox;Carol Baum and Howard Rosenman;yes");
        loadMoviesFromMockCsv();

        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertNotNull(mvcResult);
        assertTrue(result.getMin().isEmpty());
        assertTrue(result.getMax().isEmpty());
    }

    @Test
    void shouldReturnEmptyMinAndMaxWhenProducerNeverWon() throws Exception {
        addMovie("1991;Hudson Hawk;TriStar Pictures;Joel Silver;");
        addMovie("1992;Hudson Hawk 2;TriStar Pictures;Joel Silver;");
        loadMoviesFromMockCsv();

        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertNotNull(mvcResult);
        assertTrue(result.getMin().isEmpty());
        assertTrue(result.getMax().isEmpty());
    }

    @Test
    void shouldReturnEmptyMinAndMaxWhenProducersWonTwiceOnTheSameYear() throws Exception {
        addMovie("1991;Hudson Hawk;TriStar Pictures;Joel Silver;yes");
        addMovie("1991;Other title movie;TriStar Pictures;Joel Silver;yes");
        loadMoviesFromMockCsv();

        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertNotNull(mvcResult);
        assertTrue(result.getMin().isEmpty());
        assertTrue(result.getMax().isEmpty());
    }

    @Test
    void shouldReturnSameMinAndMaxWhenOnlyOneProducerWonMoreThanOnce() throws Exception {
        addMovie("1991;Hudson Hawk;TriStar Pictures;Joel Silver;yes");
        addMovie("1992;Hudson Hawk 2;TriStar Pictures;Joel Silver;yes");
        addMovie("1993;Indecent Proposal;Paramount Pictures;Sherry Lansing;yes");
        loadMoviesFromMockCsv();

        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertEquals(1, result.getMin().size());
        assertEquals(1, result.getMax().size());

        WinnerVO min = result.getMin().get(0);
        WinnerVO max = result.getMax().get(0);
        assertWinnerVoEquals(min, "Joel Silver", 1, 1991, 1992);
        assertWinnerVoEquals(max, "Joel Silver", 1, 1991, 1992);
    }

    @Test
    void shouldReturnMinAndMaxWhenProducersWonMoreThanOnce() throws Exception {
        addMovie("1991;Hudson Hawk;TriStar Pictures;Joel Silver;yes");
        addMovie("1992;Hudson Hawk 2;TriStar Pictures;Joel Silver;yes");
        addMovie("1989;Indecent Proposal;Paramount Pictures;Sherry Lansing;yes");
        addMovie("1993;Indecent Proposal 2;Paramount Pictures;Sherry Lansing;yes");
        loadMoviesFromMockCsv();

        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertEquals(1, result.getMin().size());
        assertEquals(1, result.getMax().size());

        WinnerVO min = result.getMin().get(0);
        WinnerVO max = result.getMax().get(0);
        assertWinnerVoEquals(min, "Joel Silver", 1, 1991, 1992);
        assertWinnerVoEquals(max, "Sherry Lansing", 4, 1989, 1993);
    }

    @Test
    void shouldReturnSameMinAndMaxWhenOnlyOneProducerWonMoreThanOnceLoadedMoreMovies() throws Exception {
        addAndLoadSomeMoviesFromMockCsv();

        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertEquals(1, result.getMin().size());
        assertEquals(1, result.getMax().size());

        WinnerVO min = result.getMin().get(0);
        WinnerVO max = result.getMax().get(0);
        assertWinnerVoEquals(min, "Bo Derek", 6, 1984, 1990);
        assertWinnerVoEquals(max, "Bo Derek", 6, 1984, 1990);
    }

    @Test
    void shouldReturnSameProducerButDifferentIntervalAndYearsWhenProducerWonOnDifferentYears() throws Exception {
        addMovie("1985;Bolero 2;Cannon Films;Bo Derek;yes");
        addMovie("1987;Bolero 3;Cannon Films;Bo Derek;yes");
        addAndLoadSomeMoviesFromMockCsv();

        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertEquals(1, result.getMin().size());
        assertEquals(1, result.getMax().size());

        WinnerVO min = result.getMin().get(0);
        WinnerVO max = result.getMax().get(0);
        assertWinnerVoEquals(min, "Bo Derek", 1, 1984, 1985);
        assertWinnerVoEquals(max, "Bo Derek", 3, 1987, 1990);
    }

    @Test
    void shouldReturnTwoMinsAndThreeMaxs() throws Exception {
        addMovie("1990;Star Trek VI: The Final Frontier;Paramount Pictures;Harve Bennett;yes");
        addMovie("1987;Howard the Duck 2;Universal Studios;Gloria Katz;yes");
        addMovie("1995;Cocktail 2;Touchstone Pictures;Ted Field and Robert W. Cort;yes");
        addMovie("1994;Leonard Part 6.2;Columbia Pictures;Bill Cosby;yes");
        addMovie("1989;Inchon;MGM;Mitsuharu Ishii;yes");
        addAndLoadSomeMoviesFromMockCsv();

        MvcResult mvcResult = mockMvc.perform(get(urlApi+"/winner")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        WinnerProducersVO result = convertJsonToObject(mvcResult, WinnerProducersVO.class);

        assertEquals(2, result.getMin().size());
        assertEquals(3, result.getMax().size());

        WinnerVO min0 = result.getMin().get(0);
        WinnerVO min1 = result.getMin().get(1);
        WinnerVO max0 = result.getMax().get(0);
        WinnerVO max1 = result.getMax().get(1);
        WinnerVO max2 = result.getMax().get(2);
        assertWinnerVoEquals(min0, "Gloria Katz", 1, 1986, 1987);
        assertWinnerVoEquals(min1, "Harve Bennett", 1, 1989, 1990);
        assertWinnerVoEquals(max0, "Mitsuharu Ishii", 7, 1982, 1989);
        assertWinnerVoEquals(max1, "Bill Cosby", 7, 1987, 1994);
        assertWinnerVoEquals(max2, "Ted Field and Robert W. Cort", 7, 1988, 1995);
    }
}