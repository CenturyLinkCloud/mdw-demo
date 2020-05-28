package com.centurylink.mdw.demo.bugs;

import com.centurylink.mdw.dataaccess.task.MdwTaskRefData;
import com.centurylink.mdw.model.task.TaskCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Custom TaskRefData implementation.
 */
public class Categories extends MdwTaskRefData {
    private List<TaskCategory> bugCategories;

    public Categories() {
        bugCategories = new ArrayList<>();
        bugCategories.add(new TaskCategory(201L, "ISSUE", "Bug"));
    }

    private Map<Integer,TaskCategory> taskCategories;
    @Override
    public Map<Integer,TaskCategory> getCategories() {
        if (taskCategories == null) {
            taskCategories = super.getCategories();
            for (TaskCategory bugCategory : bugCategories)
                taskCategories.put(bugCategory.getId().intValue(), bugCategory);
        }
        return taskCategories;
    }

    private Map<Integer,String> taskCategoryCodes;
    @Override
    public Map<Integer,String> getCategoryCodes() {
        if (taskCategoryCodes == null) {
            taskCategoryCodes = super.getCategoryCodes();
            for (TaskCategory bugCategory : bugCategories)
                taskCategoryCodes.put(bugCategory.getId().intValue(), bugCategory.getCode());
        }
        return taskCategoryCodes;
    }
}
