package br.com.bdws.razzieawards.controller;

import br.com.bdws.razzieawards.service.WinnerService;
import br.com.bdws.razzieawards.viewobject.WinnerProducersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class WinnerController {

    @Autowired
    private WinnerService winnerService;

    @GetMapping("/winner")
    @ResponseBody
    public WinnerProducersVO getWinnerProducers(){
        return winnerService.getWinnerProducers();
    }
}