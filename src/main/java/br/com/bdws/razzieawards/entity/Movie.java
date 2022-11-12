package br.com.bdws.razzieawards.entity;

import lombok.*;

import javax.persistence.*;

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

    @Column(nullable = false)
    private String producers;

    @Column(nullable = false)
    private boolean winner;

    public Movie(String producers, Integer movieYear) {
        this.producers = producers;
        this.movieYear = movieYear;
    }
}