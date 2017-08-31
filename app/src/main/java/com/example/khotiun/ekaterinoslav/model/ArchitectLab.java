package com.example.khotiun.ekaterinoslav.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hotun on 31.08.2017.
 */

public class ArchitectLab {
    private static ArchitectLab sArchitectLab;
    private List<Architect> mArchitects;

    public static ArchitectLab getArchitectLab() {
        if (sArchitectLab == null) {
            sArchitectLab = new ArchitectLab();
        }
        return sArchitectLab;
    }

    private ArchitectLab() {
        mArchitects = new ArrayList<>();
    }

    public List<Architect> getArchitects() {
        return mArchitects;
    }

    public Architect getArchitect(int architectId) {
        for (Architect architect : mArchitects) {
            if (architect.getId() == architectId) {
                return architect;
            }
        }
        return null;
    }

    public void addArchitectList(List<Architect> architects) {
        mArchitects.addAll(architects);
    }
}
