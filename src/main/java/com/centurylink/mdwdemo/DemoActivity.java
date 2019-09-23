package com.centurylink.mdwdemo;

import com.centurylink.mdw.activity.ActivityException;
import com.centurylink.mdw.annotations.Activity;
import com.centurylink.mdw.model.workflow.ActivityRuntimeContext;
import com.centurylink.mdw.workflow.activity.DefaultActivityImpl;
import com.centurylink.mdwdemo.greet.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Activity("Demo Activity")
@Component
@Scope("prototype")
public class DemoActivity extends DefaultActivityImpl {

    @Autowired
    private Greeting greeting;

    @Override
    public Object execute(ActivityRuntimeContext runtimeContext) throws ActivityException {
        runtimeContext.logInfo("Executing DemoActivity.  Hello, " + (greeting == null ? "" : greeting.getContent()));
        return null;
    }
}