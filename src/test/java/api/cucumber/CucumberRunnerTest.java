package api.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, tags = {"~@ignore"}, features = "src/test/resources/")
public class CucumberRunnerTest {

}

