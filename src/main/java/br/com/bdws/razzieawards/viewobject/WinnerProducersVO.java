package br.com.bdws.razzieawards.viewobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize
@Data
public class WinnerProducersVO {

    private List<WinnerVO> min = new ArrayList<>();
    private List<WinnerVO> max = new ArrayList<>();
}