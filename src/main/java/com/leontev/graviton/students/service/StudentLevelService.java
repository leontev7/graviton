package com.leontev.graviton.levels;

import java.util.ArrayList;
import java.util.List;

public class LevelService {

    private static final List<Level> LEVELS = new ArrayList<>();

    static {
        LEVELS.add(new Level("Новичок 🌱", 0));
        LEVELS.add(new Level("Школьник 🎒", 10));
        LEVELS.add(new Level("Студент 🎓", 100));
        LEVELS.add(new Level("Магистр 🧙‍♂️", 200));
        LEVELS.add(new Level("Доктор \uD83D\uDCD8", 500));
        LEVELS.add(new Level("Профессор 👨‍🏫", 1000));
        LEVELS.add(new Level("Эксперт 🦉", 2000));
        LEVELS.add(new Level("Мастер 🥋", 5000));
        LEVELS.add(new Level("Гуру 🌀", 10000));
        LEVELS.add(new Level("Великий Учитель ⛩️", 15000));
        LEVELS.add(new Level("Мудрец 🌕", 25000));
        LEVELS.add(new Level("Легенда 🌟", 50000));
    }

    public static String getLevel(int points) {
        for (int i = LEVELS.size() - 1; i >= 0; i--) {
            if (points >= LEVELS.get(i).getPoints()) {
                return LEVELS.get(i).getTitle();
            }
        }
        return "Неизвестный уровень";
    }

    public static  List<Level> getLevels() {
        return LEVELS;
    }


}
