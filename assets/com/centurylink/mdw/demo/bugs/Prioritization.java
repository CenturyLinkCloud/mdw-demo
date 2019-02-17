package com.centurylink.mdw.demo.bugs;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.centurylink.mdw.annotations.RegisteredService;
import com.centurylink.mdw.common.StrategyException;
import com.centurylink.mdw.model.task.TaskTemplate;
import com.centurylink.mdw.observer.task.ParameterizedStrategy;
import com.centurylink.mdw.observer.task.PrioritizationStrategy;

@RegisteredService(PrioritizationStrategy.class)
public class Prioritization extends ParameterizedStrategy implements PrioritizationStrategy {

    @Override
    public Date determineDueDate(TaskTemplate taskTemplate) throws StrategyException {
        Bug bug = (Bug) getParameter("request");
        if (bug.getSeverity() == null) {
            return null;  // no due date
        }
        else {
            Instant now = Instant.now();
            switch (bug.getSeverity()) {
                case 1: return Date.from(now.plus(24, ChronoUnit.HOURS));
                case 2: return Date.from(now.plus(48, ChronoUnit.HOURS));
                case 3: return Date.from(now.plus(72, ChronoUnit.HOURS));
                default: return null;
            }
        }
    }

    @Override
    public int determinePriority(TaskTemplate taskTemplate, Date dueDate) throws StrategyException {
        Bug bug = (Bug) getParameter("request");
        if (bug.getSeverity() == null) {
            return 0;  // no priority
        }
        else {
            return bug.getSeverity();
        }
    }
}
