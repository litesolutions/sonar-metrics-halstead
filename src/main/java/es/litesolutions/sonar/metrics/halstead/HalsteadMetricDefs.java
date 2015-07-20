package es.litesolutions.sonar.metrics.halstead;

import org.sonar.squid.measures.CalculatedMetricFormula;
import org.sonar.squid.measures.MetricDef;

public enum HalsteadMetricDefs
    implements MetricDef
{
    TOTAL_OPERANDS,
    DISTINCT_OPERANDS,
    TOTAL_OPERATORS,
    DISTINCT_OPERATORS,
    ;

    @Override
    public String getName()
    {
        return name();
    }

    @Override
    public boolean isCalculatedMetric()
    {
        return false;
    }

    @Override
    public boolean aggregateIfThereIsAlreadyAValue()
    {
        return false;
    }

    @Override
    public boolean isThereAggregationFormula()
    {
        return false;
    }

    @Override
    public CalculatedMetricFormula getCalculatedMetricFormula()
    {
        return null;
    }
}
