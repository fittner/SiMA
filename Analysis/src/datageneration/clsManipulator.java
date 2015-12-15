package datageneration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import logger.clsLogger;

import org.slf4j.Logger;

import utils.clsGetARSPath;
import interfaces.itfDataManipulation;

public class clsManipulator implements itfDataManipulation{
	protected static final Logger log = clsLogger.getLog("analysis.manipulator");
	private File moScenarioFile = null;
	
	public void prepareConfig(String configName) throws IOException {
		Path oSourcePath = Paths.get(clsGetARSPath.getAnalysisRunConfigPath(), "origin");
		moScenarioFile = new File(clsGetARSPath.getScenarioPath() + clsGetARSPath.getSeperator() + "analysis_scenario.properties");
		Files.createDirectories(Paths.get(clsGetARSPath.getAnalysisRunConfigPath(), configName));
		Files.walkFileTree(oSourcePath, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
            	Path oTargetPath = Paths.get(clsGetARSPath.getAnalysisRunConfigPath(), configName, file.getFileName().toString());
            	Files.copy(file, oTargetPath);
                return FileVisitResult.CONTINUE;
            }
        });
	}
	
	public File getScenarioFile() {
		return moScenarioFile;
	}
	
	@Override
	public void put(String oUniqueResourceIdentifier, String oValue) throws URISyntaxException {
		clsManipulation oManipulation = clsManipulation.create(new URI(oUniqueResourceIdentifier));
		oManipulation.setValue(oValue);
		oManipulation.run();
	}
}
