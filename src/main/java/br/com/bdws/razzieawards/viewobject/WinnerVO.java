package br.com.bdws.razzieawards.viewobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@JsonSerialize
@Builder
@Data
public class WinnerVO {
    private String producers;
    private int interval;
    private Integer previousWin;
    private Integer followingWin;
}