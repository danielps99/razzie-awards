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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "MovieProducerUK",
                        columnNames = { "movie_id", "producer_id" })
        })
public class MovieProducer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Producer producer;

    public MovieProducer(Movie movie, Producer producer) {
        this.movie = movie;
        this.producer = producer;
    }
}