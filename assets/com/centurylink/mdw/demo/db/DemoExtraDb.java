/*
 * Copyright (c) 2018 CenturyLink, Inc. All Rights Reserved.
 */
package com.centurylink.mdw.demo.db;

import java.sql.SQLException;

import com.centurylink.mdw.annotations.RegisteredService;
import com.centurylink.mdw.container.EmbeddedDbExtension;

/**
 * Custom db extension.  By default all *.sql assets in the same
 * package and this dynamic java class will be sourced as part of the
 * one-time initialization for this db.
 */
@RegisteredService(EmbeddedDbExtension.class)
public class DemoExtraDb implements EmbeddedDbExtension {

    public void initialize() throws SQLException {
    }
}
