package testRunner;
import org.junit.runner.RunWith;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/rerun/cucumber.json",
        jsonUsageReport = "target/rerun/cucumber-usage.json",
        usageReport = true,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        overviewChartsReport = true,
        pdfPageSize = "A4 Landscape",
        toPDF = true,
        outputFolder = "target/rerun",
        retryCount = 1)
@CucumberOptions(features = "src/test/resources/features/EnergyAustralia.feature", glue = { "stepDefinition" }, plugin = {"pretty", "html:src/test/resources/reports/cucumber-reports.html"}, monochrome = true)

public class TestRunner {
	

}