package es.litesolutions.sonar.metrics.halstead;

import com.google.common.collect.ImmutableList;
import org.sonar.api.measures.Formula;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.api.measures.SumChildValuesFormula;

import java.util.List;

public final class HalsteadMetrics
    implements Metrics
{
    private static final String DOMAIN = "Halstead";

    public static final Metric<Integer> TOTAL_OPERANDS;
    public static final Metric<Integer> DISTINCT_OPERANDS;
    public static final Metric<Integer> TOTAL_OPERATORS;
    public static final Metric<Integer> DISTINCT_OPERATORS;

    public static final Metric<Integer> VOCABULARY;
    public static final Metric<Integer> LENGTH;

    public static final Metric<Double> CALCULATED_LENGTH;
    public static final Metric<Double> VOLUME;

    public static final Metric<Double> DIFFICULTY;
    public static final Metric<Double> EFFORT;

    public static final Metric<Integer> TIME;
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
