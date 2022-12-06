package com.likebookapp.init;

import com.likebookapp.service.MoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StylesInit implements CommandLineRunner {

    private final MoodServiceImpl moodService;

    @Autowired
    public StylesInit(MoodServiceImpl moodService) {
        this.moodService = moodService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.moodService.initMoods();



    }
}
