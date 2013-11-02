package com.dw.maven.plugins;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.Collection;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Goal closure compile
 * @goal compile
 * @phase process-sources
 */
public class ClosureCompilerMojo extends AbstractMojo {
    /**
     * Compilation level.
     * @parameter expression="${compilation.level}" default-value="SIMPLE_OPTIMIZATIONS"
     */
    private String compilation_level;

    /**
     * Js directory.
     * @parameter expression="${inputDir}"
     */
    private File inputDir;

    /**
     * Js output directory.
     * @parameter expression="${outputDir}"
     */
    private File outputDir;

    /**
     * Version.
     * @parameter expression="${version}"
     */
    private String version;

    /**
     * Recursive.
     * @parameter expression="${recursive}" default-value="true"
     */
    private boolean recursive;

    /**
     * Execute.
     * @throws org.apache.maven.plugin.MojoExecutionException The exception.
     */
    public void execute() throws MojoExecutionException {
        SecurityManager defaultSecurityManager = System.getSecurityManager();
        System.setSecurityManager(new NoExitSecurityManager()); // needed because System.exit is called by the runner.
        if (isValid(inputDir) && isValid(outputDir)) {
            getLog().info("Scanning input directory: " + inputDir.getPath() + ". Recursive mode = " + recursive);
            Collection<File> inputFiles = FileUtils.listFiles(inputDir, new String[] {"js"}, recursive);
            for (File js : inputFiles) {
                compile(js);
            }
        } else {
            getLog().error("Invalid input/output directories.");
        }
        System.setSecurityManager(defaultSecurityManager); // set back to original security manager.
    }

    /**
     * Run the compile.
     * @param js The js to compile.
     */
    private void compile(final File js) {
        final Boolean useVersion = isEmpty(version) ? false : true;
        final String target = targetPath(useVersion, js);
        final String source = sourcePath(js);
        final ClosureCompilerRunner runner = new ClosureCompilerRunner(compilation_level, source, target);
        if (runner.shouldRunCompiler()) {
            try {
                runner.run();
            } catch (SecurityException e) {
                // expected throw when run finishes it calls System.exit.
            }
        }
        getLog().debug("Compiled file: " + source + " to file: " + target);
    }

    /**
     * Gets the source path.
     * @param source The source file.
     * @return path The source path.
     */
    private String sourcePath(final File source) {
        return source.getPath();
    }

    /**
     * Gets the target path.
     * @param useVersion Indicates use version.
     * @param fileName The filename.
     * @return target The target.
     */
    private String targetPath(final Boolean useVersion, final File inputFile) {
        final StringBuilder builder = new StringBuilder(outputDir.getPath());
        
        String inputFilePath = inputFile.getPath();
        String fileName = inputFilePath.substring(inputDir.getPath().length());
        
        builder.append(File.separator);
        builder.append(useVersion ? fileName.replace(".js", "-" + version + ".js") : fileName);
        return builder.toString();
    }

    /**
     * Indicates if the given directory path is a valid file directory.
     * @param file The file.
     * @return <code>true</code> if the directory exists and is a directory, else <code>false</code>.
     */
    private boolean isValid(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }
}
