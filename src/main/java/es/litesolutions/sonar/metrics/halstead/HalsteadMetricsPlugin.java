package es.litesolutions.sonar.metrics.halstead;

import org.sonar.api.SonarPlugin;

import java.util.Collections;
import java.util.List;

/**
 * The plugin file
 */
public final class HalsteadMetricsPlugin
    extends SonarPlugin
{
    @Override
    @SuppressWarnings("rawtypes")
    public List getExtensions()
    {
        return Collections.singletonList(HalsteadMetrics.class);
    }
}
