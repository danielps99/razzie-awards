package br.com.bdws.razzieawards.viewobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@JsonSerialize
@Builder
@Data
@Accessors(chain = true)
public class WinnerVO {
    private String producers;
    private Integer previousWin;
    private Integer followingWin;

    public int getInterval() {
        return hasInterval() ? followingWin - previousWin : 0;
    }

    public boolean hasInterval() {
        return previousWin != null && followingWin != null;
    }
}