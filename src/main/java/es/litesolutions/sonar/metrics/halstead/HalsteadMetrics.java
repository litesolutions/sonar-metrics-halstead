package es.litesolutions.sonar.metrics.halstead;

import com.google.common.collect.ImmutableList;
import org.sonar.api.measures.Formula;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.api.measures.SumChildValuesFormula;

import java.util.List;

/**
 * All Halstead metrics
 *
 * <p>The metrics are of two types:</p>
 *
 * <ul>
 *     <li>metrics for all basic measurements (see {@link HalsteadMetricDefs};
 *     </li>
 *     <li>calculated metrics.</li>
 * </ul>
 *
 * <p>See <a
 * href="https://en.wikipedia.org/wiki/Halstead_complexity_measures">the
 * Wikipedia entry</a> for Halstead complexity measures for a description of all
 * measures.</p>
 *
 * <p>Aggregation for basic measurements is done from individual source files up
 * to the project level using a {@link SumChildValuesFormula} (with the boolean
 * argument of the constructor being {@code true}; this means 0 will be supplied
 * for a file if there is no value). Note that this is the responsibility of the
 * implementation to actually persist those metric values.</p>
 *
 * <p>The domain associated with all metrics is {@code Halstead}; the metric key
 * is {@code halstead_xxx}, where `xxx` is the metric name in lowercase.</p>
 *
 * @see HalsteadFormula
 */
public final class HalsteadMetrics
    implements Metrics
{
    private static final String DOMAIN = "Halstead";

    /**
     * Total number of operands
     *
     * @see HalsteadMetricDefs#TOTAL_OPERANDS
     */
    public static final Metric<Integer> TOTAL_OPERANDS;

    /**
     * Number of distinct operands
     *
     * @see HalsteadMetricDefs#DISTINCT_OPERANDS
     */
    public static final Metric<Integer> DISTINCT_OPERANDS;

    /**
     * Total number of operators
     *
     * @see HalsteadMetricDefs#TOTAL_OPERATORS
     */
    public static final Metric<Integer> TOTAL_OPERATORS;

    /**
     * Number of distinct opertors
     *
     * @see HalsteadMetricDefs#DISTINCT_OPERATORS
     */
    public static final Metric<Integer> DISTINCT_OPERATORS;

    /**
     * Vocabulary (computed)
     *
     * @see HalsteadFormula#VOCABULARY
     */
    public static final Metric<Integer> VOCABULARY;

    /**
     * Length (computed)
     *
     * @see HalsteadFormula#LENGTH
     */
    public static final Metric<Integer> LENGTH;

    /**
     * Calculated length (computed)
     *
     * @see HalsteadFormula#CALCULATED_LENGTH
     */
    public static final Metric<Double> CALCULATED_LENGTH;

    /**
     * Volume (computed)
     *
     * @see HalsteadFormula#VOLUME
     */
    public static final Metric<Double> VOLUME;

    /**
     * Difficulty (computed)
     *
     * @see HalsteadFormula#DIFFICULTY
     */
    public static final Metric<Double> DIFFICULTY;

    /**
     * Effort (computed)
     *
     * @see HalsteadFormula#EFFORT
     */
    public static final Metric<Double> EFFORT;

    /**
     * Estimated time to program (computed)
     *
     * <p>Note that the type of this metric is {@link
     * org.sonar.api.measures.Metric.ValueType#WORK_DUR}.</p>
     *
     * @see HalsteadFormula#TIME
     */
    public static final Metric<Integer> TIME;

    /**
     * Estimated delivered bugs (computed)
     *
     * @see HalsteadFormula#BUGS
     */
    public static final Metric<Double> BUGS;

    @SuppressWarnings("rawtypes")
    private static final List<Metric> METRICS;

    private static final Formula CHILD_SUM = new SumChildValuesFormula(true);

    static {
        @SuppressWarnings("rawtypes")
        final ImmutableList.Builder<Metric> builder
            = ImmutableList.builder();

        TOTAL_OPERANDS = new Metric.Builder("halstead_total_operands",
            "Total operands", Metric.ValueType.INT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(CHILD_SUM)
            .create();
        builder.add(TOTAL_OPERANDS);

        DISTINCT_OPERANDS = new Metric.Builder("halstead_distinct_operands",
            "Distinct operands", Metric.ValueType.INT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(CHILD_SUM)
            .create();
        builder.add(DISTINCT_OPERANDS);

        TOTAL_OPERATORS = new Metric.Builder("halstead_total_operators",
            "Total operators", Metric.ValueType.INT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(CHILD_SUM)
            .create();
        builder.add(TOTAL_OPERATORS);

        DISTINCT_OPERATORS = new Metric.Builder("halstead_distinct_operators",
            "Distinct operators", Metric.ValueType.INT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(CHILD_SUM)
            .create();
        builder.add(DISTINCT_OPERATORS);

        VOCABULARY = new Metric.Builder("halstead_vocabulary", "Vocabulary",
            Metric.ValueType.INT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(HalsteadFormula.VOCABULARY)
            .create();
        builder.add(VOCABULARY);

        LENGTH = new Metric.Builder("halstead_length", "Length",
            Metric.ValueType.INT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(HalsteadFormula.LENGTH)
            .create();
        builder.add(LENGTH);

        CALCULATED_LENGTH = new Metric.Builder("halstead_calculated_length",
            "Calculated length", Metric.ValueType.FLOAT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(HalsteadFormula.CALCULATED_LENGTH)
            .create();
        builder.add(CALCULATED_LENGTH);

        VOLUME = new Metric.Builder("halstead_volume", "Volume",
            Metric.ValueType.FLOAT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(HalsteadFormula.VOLUME)
            .create();
        builder.add(VOLUME);

        DIFFICULTY = new Metric.Builder("halstead_difficulty", "Difficulty",
            Metric.ValueType.FLOAT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(HalsteadFormula.DIFFICULTY)
            .create();
        builder.add(DIFFICULTY);

        EFFORT = new Metric.Builder("halstead_effort", "Effort",
            Metric.ValueType.FLOAT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(HalsteadFormula.EFFORT)
            .create();
        builder.add(EFFORT);

        TIME = new Metric.Builder("halstead_time", "Time to program",
            Metric.ValueType.WORK_DUR)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(HalsteadFormula.TIME)
            .create();
        builder.add(TIME);

        BUGS = new Metric.Builder("halstead_bugs", "Estimated bugs",
            Metric.ValueType.FLOAT)
            .setDomain(DOMAIN)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setFormula(HalsteadFormula.BUGS)
            .create();
        builder.add(BUGS);

        METRICS = builder.build();
    }

    @SuppressWarnings({ "ReturnOfCollectionOrArrayField", "rawtypes" })
    @Override
    public List<Metric> getMetrics()
    {
        return METRICS;
    }
}
