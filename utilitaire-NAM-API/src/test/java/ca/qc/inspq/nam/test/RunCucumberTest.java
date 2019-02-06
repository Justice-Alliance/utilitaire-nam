package ca.qc.inspq.nam.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "pretty" },
		features = {"src/test/java/ca/qc/inspq/nam/test/UtilitaireNAM.feature"},
		glue = {"stepdefs"}
		
)
public class RunCucumberTest {

}