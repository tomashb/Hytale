package com.example.hytale.scoreboard.adapter;

import java.util.List;

public interface ScoreboardView {
    void setTitle(String title);

    void setLines(List<String> lines);

    void show();

    void hide();

    void destroy();
}
