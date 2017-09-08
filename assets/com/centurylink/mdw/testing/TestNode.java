package com.centurylink.mdw.testing;

import com.eclipsesource.v8.NodeJS;

public class TestNode {
	
	public static void main(String[] args) {
        NodeJS nodeJS = NodeJS.createNodeJS();
        System.out.println("NODE JS: " + nodeJS.getNodeVersion());
	}

}
