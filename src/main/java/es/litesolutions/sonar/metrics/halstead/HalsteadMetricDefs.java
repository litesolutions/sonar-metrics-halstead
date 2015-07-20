package es.litesolutions.sonar.metrics.halstead;

import org.sonar.squid.measures.CalculatedMetricFormula;
import org.sonar.squid.measures.MetricDef;

/**
 * Basic Halstead metric elements
 *
 * <p>Note that providing a value to these elements is left to the
 * implementation. These need to be written per source file. The aggregation of
 * values is performed by the associated metrics.</p>
 */
public enum HalsteadMetricDefs
    implements MetricDef
{
    /**
     * The total number of operands in the source file
     *
     * @see HalsteadMetrics#TOTAL_OPERANDS
     */
    TOTAL_OPERANDS,

    /**
     * The numer of distinct operands in the source file
     *
     * @see HalsteadMetrics#DISTINCT_OPERANDS
     */
    DISTINCT_OPERANDS,

    /**
     * The total number of operators in the source file
     *
     * @see HalsteadMetrics#TOTAL_OPERATORS
     */
    TOTAL_OPERATORS,

    /**
     * The numer of distinct operators in the source file
     *
     * @see HalsteadMetrics#DISTINCT_OPERATORS
     */
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
