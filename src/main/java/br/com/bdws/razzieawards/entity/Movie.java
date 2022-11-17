package br.com.bdws.razzieawards.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Movie {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer movieYear;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String studios;

    private String producersOld;

    @Column(nullable = false)
    private boolean winner;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovieProducer> producers;

    public Movie(String producersOld, Integer movieYear) {
        this.producersOld = producersOld;
        this.movieYear = movieYear;
    }
}