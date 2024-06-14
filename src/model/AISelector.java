package model;

import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.view.View;

import java.util.Map;
import java.util.HashMap;

public class AISelector {
    private static final Map<Integer, Decider> aiMap = new HashMap<>();

    public static void setAI(int playerIndex, Decider decider) {
        aiMap.put(playerIndex, decider);
    }

    public static Decider getDecider(int playerIndex) {
        return aiMap.get(playerIndex);
    }
}
