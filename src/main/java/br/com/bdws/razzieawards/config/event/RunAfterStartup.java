package br.com.bdws.razzieawards.config.event;

import br.com.bdws.razzieawards.service.DataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RunAfterStartup {

    @Autowired
    private DataLoaderService dataLoader;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        dataLoader.loadMoviesFromCsv();
    }
}
