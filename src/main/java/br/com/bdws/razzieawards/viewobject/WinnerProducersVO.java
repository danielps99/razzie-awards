package br.com.bdws.razzieawards.viewobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize
@Data
@AllArgsConstructor
public class WinnerProducersVO {

    private List<WinnerVO> min;
    private List<WinnerVO> max;
}