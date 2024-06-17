package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;

public class K_NaiveDecider extends Decider {
    public K_NaiveDecider(Model model, Controller controller) {
        super(model, controller);
    }

    @Override
    public ActionList decide() {
        // TODO : implement the naive decider
        System.out.println("Naive Decider");
        return null;
    }
}
