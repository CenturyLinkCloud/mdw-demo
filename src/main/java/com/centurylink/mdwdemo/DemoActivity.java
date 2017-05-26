package com.centurylink.mdwdemo;

import com.centurylink.mdw.activity.ActivityException;
import com.centurylink.mdw.model.workflow.ActivityRuntimeContext;
import com.centurylink.mdw.util.log.StandardLogger.LogLevel;
import com.centurylink.mdw.util.timer.Tracked;
import com.centurylink.mdw.workflow.activity.DefaultActivityImpl;

@Tracked(LogLevel.TRACE)
public class DemoActivity extends DefaultActivityImpl {

    @Override
    public Object execute(ActivityRuntimeContext runtimeContext) throws ActivityException {
        runtimeContext.logInfo("Executing DemoActivity (does nothing)");
        return null;
    }
}