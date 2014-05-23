package org.codefirst.sapid;

import org.sapid.tool.checker.jx.Main;

/**
 * Main class.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("start");
        Main.main(new String[] { "-v", "-sdb:src/test/resources/SDB",
                "-c:src/main/resources/config.xml",
                "src/test/resources/test/Test1.java" });
        System.out.println("end");
    }
}
