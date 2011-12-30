/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wb.downloadfolder.test;

import java.io.File;
import org.apache.camel.Exchange;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wb.downloadfolder.route.DownloadFolderRoute;

/**
 *
 * @author martinh
 */
public class DLFolderRouteTester extends CamelSpringTestSupport {

    public DLFolderRouteTester() {
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        context.addRoutes(new DownloadFolderRoute());
        context.setTracing(true);
        deleteDirectory("target/inbox");
        deleteDirectory("target/outbox");
        Thread.sleep(3000);
    }

    @After
    public void tearDown() {
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("TestAppContext.xml");
    }

    @Test
    public void testIt() throws Exception {
        executeAndTest("pdf", "target/inbox/DEV/docs");
        executeAndTest("key", "target/inbox/Documents");
        executeAndTest("ppt", "target/inbox/Documents");
        executeAndTest("pptx", "target/inbox/Documents");
        executeAndTest("pls", "target/inbox/Music");
        executeAndTest("m3u", "target/inbox/Music");
        executeAndTest("flv", "target/inbox/Movies");
        executeAndTest("mp4", "target/inbox/Movies");
        executeAndTest("avi", "target/inbox/Movies");
        executeAndTest("mov", "target/inbox/Movies");
        //executeAndTest("", "target/inbox/undone");
        executeAndTest("xyz", "target/inbox/undone");
        executeAndTest("app", "target/inbox/Temp/Apps");
        executeAndTest("dmg", "target/inbox/Temp/Apps");
        executeAndTest("tar", "target/inbox/software");
        executeAndTest("gz", "target/inbox/software");
        executeAndTest("zip", "target/inbox/software");

    }
    
    private void executeAndTest(String type, String folder) throws Exception{
        String bodyOfMessage = "Test";
        String filename = "test." + type;
        template.sendBodyAndHeader("file://target/inbox", bodyOfMessage.getBytes(),
                Exchange.FILE_NAME, filename);

        Thread.sleep(1000);
        File target = new File(folder + "/" + filename);
        assertTrue("file not copied" + type, target.exists());
    }
    
}
