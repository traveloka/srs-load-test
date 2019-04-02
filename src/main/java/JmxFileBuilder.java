package main.java;
/**
 * // TODO Comment
 */

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JmxFileBuilder {
  public static int noOfThreads = 10;

  public void generateJmxFile(String jsonString, int k) throws IOException {

    String slash = System.getProperty("file.separator");
      String fileName = "example" + k + ".jmx";
      File jmeterProperties = new JsonObjectBuilder().getFile("jmeter.properties");
      if (jmeterProperties.exists()) {

        JMeterUtils.setJMeterHome("./resources/");
        JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());

        HashTree testPlanTree = new HashTree();

        HTTPSamplerProxy blazemetercomSampler = new HTTPSamplerProxy();
        blazemetercomSampler.setName("Open blazemeter.com");
        blazemetercomSampler.setProperty("HTTPSampler.postBodyRaw", true);
        blazemetercomSampler.addArgument("", jsonString);
        blazemetercomSampler.setProperty("HTTPSampler.domain", "locsrch-shared-stg.test.tvlk.cloud");
        blazemetercomSampler.setProperty("HTTPSampler.port", "");
        blazemetercomSampler.setProperty("HTTPSampler.connect_timeout", "");
        blazemetercomSampler.setProperty("HTTPSampler.response_timeout", "");
        blazemetercomSampler.setProperty("HTTPSampler.protocol", "https");
        blazemetercomSampler.setProperty("HTTPSampler.contentEncoding", "");
        blazemetercomSampler.setProperty("HTTPSampler.path", "search");
        blazemetercomSampler.setProperty("HTTPSampler.method", "POST");
        blazemetercomSampler.setProperty("HTTPSampler.follow_redirects", false);
        blazemetercomSampler.setProperty("HTTPSampler.use_keepalive", true);
        blazemetercomSampler.setProperty("HTTPSampler.DO_MULTIPART_POST", false);
        blazemetercomSampler.setProperty("HTTPSampler.implementation", "HttpClient4");
        blazemetercomSampler.setProperty("HTTPSampler.monitor", false);
        blazemetercomSampler.setProperty("HTTPSampler.embedded_url_re", "");
        blazemetercomSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        blazemetercomSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());


        LoopController loopController = new LoopController();
        loopController.setLoops(1);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();

        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Example Thread Group");
        threadGroup.setNumThreads(noOfThreads);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        HashTree hashTree = new HashTree();
        CSVDataSet csvDataSet = new CSVDataSet();
        csvDataSet.setName("search");
        csvDataSet.setProperty("delimiter", ":");
        csvDataSet.setProperty("filename", "CSVFile.csv");
        csvDataSet.setProperty("ignoreFirstLine", true);
        csvDataSet.setProperty("quotedData", false);
        csvDataSet.setProperty("recycle", true);
        csvDataSet.setProperty("shareMode", "shareMode.all");
        csvDataSet.setProperty("stopThread", false);
        csvDataSet.setProperty("variableNames", "geoId,duration_min,duration_max,discount_min,discount_max,rating_min,rating_max,gType,lat,lon,trending_bool,specialOffers_bool,instantVoucher_bool,open24Hours_bool,theme_values,active_bool,subCategories_values,tags,facilities,foodRestriction,cuisines,dishes");
        csvDataSet.setProperty(TestElement.TEST_CLASS, csvDataSet.getClass().getName());
        csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        hashTree.add(csvDataSet);

        TestPlan testPlan = new TestPlan("ESTesting");
        testPlan.setEnabled(true);
        testPlan.setProperty("TestPlan.comments", "");
        testPlan.setProperty("TestPlan.functional_mode", false);
        testPlan.setProperty("TestPlan.serialize_threadgroups", false);
        testPlan.setProperty("TestPlan.user_define_classpath", "");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        testPlanTree.add(testPlan);
        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        threadGroupHashTree.add(blazemetercomSampler);
        threadGroupHashTree.add(hashTree);
        SaveService.saveTree(testPlanTree, new FileOutputStream("./JMXFiles" + slash + fileName));


      }


      System.out.println("JMeter .jmx script is available at " + "./JMXFiles" + slash + fileName);




  }
}
