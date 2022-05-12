package test;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.service.ExtentService;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		plugin = {
				"pretty",
				"json:output/json-report/cucumber.json",
				"com.epam.reportportal.cucumber.ScenarioReporter",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		},
		features = "src/feature",
		glue = { "stepdefinition" },
		tags = "@MyObservatory_001"
)

public class TestRun extends AbstractTestNGCucumberTests {
	public static ITestContext itc;
	
	@BeforeSuite
	public void beforeSuite(ITestContext itc) throws Exception {
		this.itc = itc;
		//Thread.sleep(15000);
	}

		public void copyFolder(Path src, Path dest) throws IOException {
		try (Stream<Path> stream = Files.walk(src)) {
			stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
		}
	}

	private void copy(Path source, Path dest) {
		try {
			Files.deleteIfExists(dest);
			Files.copy(source, dest, REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
   