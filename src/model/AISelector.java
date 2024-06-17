package model;

import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.view.View;

import java.util.Map;
import java.util.HashMap;

public class AISelector {
    private static final Map<Integer, String> aiMap = new HashMap<>();

    public static void setAI(int playerIndex, String typeAI) {
        aiMap.put(playerIndex, typeAI);
    }

    public static String getDecider(int playerIndex) {
        return aiMap.get(playerIndex);
    }
}
