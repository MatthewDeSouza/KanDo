package com.groupfive.kando.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.classic.Logger;

/**
 * A custom filter for Logback's {@link Logger} class. Used to filter out log
 * messages from packages outside the {@link com.groupfive.kando} package.
 *
 * @author Matthew DeSouza
 */
public class PackageFilter extends Filter<ILoggingEvent> {
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().startsWith(packageName)) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
