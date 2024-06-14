package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;

public class HoleNaiveDecider extends Decider {
    public HoleNaiveDecider(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {
        // TODO : implement the naive decider
        return null;
    }
}
