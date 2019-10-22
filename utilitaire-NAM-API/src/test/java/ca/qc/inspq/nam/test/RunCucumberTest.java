package ca.qc.inspq.nam.test;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "pretty" },
		features = {"src/test/java/ca/qc/inspq/nam/test/UtilitaireNAM.feature"},
		glue = {"stepdefs"}
		
)
public class RunCucumberTest {

}