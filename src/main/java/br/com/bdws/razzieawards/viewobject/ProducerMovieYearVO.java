package br.com.bdws.razzieawards.viewobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ProducerMovieYearVO {
    private String producer;
    private Integer movieYear;
}