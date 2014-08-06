package com.bertothy.xavier.PascalSierpinsky;

import java.math.BigInteger;

import com.bertothy.xavier.PascalSierpinski.model.Node;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class NodeTest extends TestCase{

    public NodeTest( String testName )
    {
        super( testName );
        System.out.println(testName);
    }

    public static Test suite()
    {
    	System.out.println("suite");
        TestSuite suite = new TestSuite();
        suite.addTest(new NodeTest("buildRootNode"));
        return suite;
    }

	public void buildRootNode()
    {
		Node root = new Node(new BigInteger("1"), 1);
        assertEquals( new BigInteger("1"), root.getNumber());
        assertEquals( 1, root.getLevel());
    }

}
