package com.likebookapp.service;

import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.MoodsEnum;
import com.likebookapp.repository.MoodsRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class MoodServiceImpl implements MoodService {

    private final MoodsRepository moodsRepository;

    public MoodServiceImpl(MoodsRepository moodsRepository) {
        this.moodsRepository = moodsRepository;
    }

    @Override
    public void initMoods() {
        if (this.moodsRepository.count() != 0) {
            return;
        }
        Arrays.stream(MoodsEnum.values())
                .forEach(s -> {
                    Mood mood = new Mood();
                    mood.setMoodName(s);
                    mood.setDescription("...");
                    moodsRepository.save(mood);
                });
    }

    @Override
    public Mood findMood(MoodsEnum moodsEnum) {
        return this.moodsRepository.findByMoodName(moodsEnum).orElseThrow();
    }
}
