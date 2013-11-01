package com.dw.maven.plugins;

import org.apache.maven.plugin.logging.Log;

/**
 * Mojo logger.
 * @author mischa
 */
public class MojoLogger implements Log {
	
    private StringBuilder debug = new StringBuilder();
    private StringBuilder info = new StringBuilder();
    private StringBuilder warn = new StringBuilder();
    private StringBuilder error = new StringBuilder();

    public boolean isDebugEnabled() {
        return true;
    }

    public void debug(CharSequence charSequence) {
        debug.append(charSequence);
    }

    public void debug(CharSequence charSequence, Throwable throwable) {
        debug.append(charSequence);
        debug.append(throwable.getMessage());
    }

    public void debug(Throwable throwable) {
        debug.append(throwable.getMessage());
    }

    /**
     * Debug.
     * @return debug The debug.
     */
    public String debug() {
        return debug.toString();
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public void info(CharSequence charSequence) {
        info.append(charSequence);
    }

    public void info(CharSequence charSequence, Throwable throwable) {
        info.append(charSequence);
        info.append(throwable.getMessage());
    }

    public void info(Throwable throwable) {
        info.append(throwable.getMessage());
    }

    /**
     * Info.
     * @return info The info.
     */
    public String info() {
        return info.toString();
    }

    public boolean isWarnEnabled() {
        return true;
    }

    public void warn(CharSequence charSequence) {
        warn.append(charSequence);
    }

    public void warn(CharSequence charSequence, Throwable throwable) {
        warn.append(charSequence);
        warn.append(throwable.getMessage());
    }

    public void warn(Throwable throwable) {
        warn.append(throwable.getMessage());
    }

    /**
     * Warn.
     * @return warn The warn.
     */
    public String warn() {
        return warn.toString();
    }

    public boolean isErrorEnabled() {
        return true;
    }

    public void error(CharSequence charSequence) {
        error.append(charSequence);
    }

    public void error(CharSequence charSequence, Throwable throwable) {
        error.append(charSequence);
        error.append(throwable.getMessage());
    }

    public void error(Throwable throwable) {
        error.append(throwable.getMessage());
    }

    /**
     * Error.
     * @return error The error.
     */
    public String error() {
        return error.toString();
    }

    public void reset() {
        debug = new StringBuilder();
        info = new StringBuilder();
        warn = new StringBuilder();
        error = new StringBuilder();
    }
}
