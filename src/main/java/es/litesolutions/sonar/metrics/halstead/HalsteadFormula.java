package es.litesolutions.sonar.metrics.halstead;


import org.sonar.api.measures.Formula;
import org.sonar.api.measures.FormulaContext;
import org.sonar.api.measures.FormulaData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Halstead computed metrics formulae
 *
 * <p>Those formulae are copied straight off <a
 * href="https://en.wikipedia.org/wiki/Halstead_complexity_measures">the
 * Wikipedia entry</a>.</p>
 *
 * <p>Implementation notes:</p>
 *
 * <ul>
 *     <li>if the value for a given dependent metric is 0 and a logarithm or
 *     division is required, the equivalent term of the formula will be set to
 *     0;</li>
 *     <li>logarithm and exponentiation calculations are done using {@link
 *     Math}, not {@link StrictMath}.</li>
 * </ul>
 */
@SuppressWarnings("rawtypes")
@ParametersAreNonnullByDefault
public enum HalsteadFormula
    implements Formula
{
    /**
     * Vocabulary formula
     *
     * <p>Dependencies:</p>
     *
     * <ul>
     *     <li>{@link HalsteadMetrics#DISTINCT_OPERANDS};</li>
     *     <li>{@link HalsteadMetrics#DISTINCT_OPERATORS}.</li>
     * </ul>
     *
     * @see HalsteadMetrics#VOCABULARY
     */
    VOCABULARY {
        @Override
        public List<Metric> dependsUponMetrics()
        {
            return Arrays.<Metric>asList(
                HalsteadMetrics.DISTINCT_OPERANDS,
                HalsteadMetrics.DISTINCT_OPERATORS
            );
        }

        @Override
        public Measure calculate(final FormulaData data,
            final FormulaContext context)
        {
            final double operands = data
                .getMeasure(HalsteadMetrics.DISTINCT_OPERANDS).getValue();
            final double operators = data
                .getMeasure(HalsteadMetrics.DISTINCT_OPERATORS).getValue();
            return new Measure(context.getTargetMetric(), operands + operators);
        }
    },

    /**
     * Length formula
     *
     * <p>Dependencies:</p>
     *
     * <ul>
     *     <li>{@link HalsteadMetrics#TOTAL_OPERANDS};</li>
     *     <li>{@link HalsteadMetrics#TOTAL_OPERATORS}.</li>
     * </ul>
     *
     * @see HalsteadMetrics#LENGTH
     */
    LENGTH {
        @Override
        public List<Metric> dependsUponMetrics()
        {
            return Arrays.<Metric>asList(
                HalsteadMetrics.TOTAL_OPERANDS,
                HalsteadMetrics.TOTAL_OPERATORS
            );
        }

        @Override
        public Measure calculate(final FormulaData data,
            final FormulaContext context)
        {
            final double operands = data
                .getMeasure(HalsteadMetrics.TOTAL_OPERANDS).getValue();
            final double operators = data
                .getMeasure(HalsteadMetrics.TOTAL_OPERATORS).getValue();
            return new Measure(context.getTargetMetric(),
                operands + operators);
        }
    },

    /**
     * Calculated length formula
     *
     * <p>Dependencies:</p>
     *
     * <ul>
     *     <li>{@link HalsteadMetrics#DISTINCT_OPERANDS};</li>
     *     <li>{@link HalsteadMetrics#DISTINCT_OPERATORS}.</li>
     * </ul>
     *
     * @see HalsteadMetrics#CALCULATED_LENGTH
     */
    CALCULATED_LENGTH {
        @Override
        public List<Metric> dependsUponMetrics()
        {
            return Arrays.<Metric>asList(
                HalsteadMetrics.DISTINCT_OPERANDS,
                HalsteadMetrics.DISTINCT_OPERATORS
            );
        }

        @Override
        public Measure calculate(final FormulaData data,
            final FormulaContext context)
        {
            final double operands = data
                .getMeasure(HalsteadMetrics.DISTINCT_OPERANDS).getValue();
            final double operators = data
                .getMeasure(HalsteadMetrics.DISTINCT_OPERATORS).getValue();
            double value = 0.0;
            if (operands != 0.0)
                value += operands * Math.log(operands);
            if (operators != 0.0)
                value += operators * Math.log(operators);
            return new Measure(context.getTargetMetric(), value);
        }
    },

    /**
     * Volume formula
     *
     * <p>Dependencies:</p>
     *
     * <ul>
     *     <li>{@link HalsteadMetrics#VOCABULARY};</li>
     *     <li>{@link HalsteadMetrics#LENGTH}.</li>
     * </ul>
     *
     * @see HalsteadMetrics#VOLUME
     */
    VOLUME {
        @Override
        public List<Metric> dependsUponMetrics()
        {
            return Arrays.<Metric>asList(
                HalsteadMetrics.VOCABULARY,
                HalsteadMetrics.LENGTH
            );
        }

        @Override
        public Measure calculate(final FormulaData data,
            final FormulaContext context)
        {
            final double vocabulary = data
                .getMeasure(HalsteadMetrics.VOCABULARY).getValue();
            final double length = data.getMeasure(HalsteadMetrics.LENGTH)
                .getValue();
            final double value = vocabulary == 0.0 ? 0
                : length * Math.log(vocabulary);
            return new Measure(context.getTargetMetric(), value);
        }
    },

    /**
     * Difficulty formula
     *
     * <p>Dependencies:</p>
     *
     * <ul>
     *     <li>{@link HalsteadMetrics#DISTINCT_OPERATORS};</li>
     *     <li>{@link HalsteadMetrics#TOTAL_OPERANDS};</li>
     *     <li>{@link HalsteadMetrics#DISTINCT_OPERANDS}.</li>
     * </ul>
     *
     * @see HalsteadMetrics#DIFFICULTY
     */
    DIFFICULTY {
        @Override
        public List<Metric> dependsUponMetrics()
        {
            return Arrays.<Metric>asList(
                HalsteadMetrics.DISTINCT_OPERATORS,
                HalsteadMetrics.TOTAL_OPERANDS,
                HalsteadMetrics.DISTINCT_OPERANDS
            );
        }

        @Override
        public Measure calculate(final FormulaData data,
            final FormulaContext context)
        {
            final double distinctOperators = data
                .getMeasure(HalsteadMetrics.DISTINCT_OPERATORS).getValue();
            final double distinctOperands = data
                .getMeasure(HalsteadMetrics.DISTINCT_OPERANDS).getValue();
            final double totalOperands = data
                .getMeasure(HalsteadMetrics.TOTAL_OPERANDS).getValue();
            double value = distinctOperators / 2.0;
            if (distinctOperands != 0.0)
                value += totalOperands / distinctOperands;
            return new Measure(context.getTargetMetric(), value);
        }
    },

    /**
     * Estimated effort formula
     *
     * <p>Dependencies:</p>
     *
     * <ul>
     *     <li>{@link HalsteadMetrics#VOLUME};</li>
     *     <li>{@link HalsteadMetrics#DIFFICULTY}.</li>
     * </ul>
     *
     * @see HalsteadMetrics#EFFORT
     */
    EFFORT {
        @Override
        public List<Metric> dependsUponMetrics()
        {
            return Arrays.<Metric>asList(
                HalsteadMetrics.VOLUME,
                HalsteadMetrics.DIFFICULTY
            );
        }

        @Override
        public Measure calculate(final FormulaData data,
            final FormulaContext context)
        {
            final double volume = data.getMeasure(HalsteadMetrics.VOLUME)
                .getValue();
            final double difficulty
                = data.getMeasure(HalsteadMetrics.DIFFICULTY).getValue();
            return new Measure(context.getTargetMetric(), volume * difficulty);
        }
    },

    /**
     * Estimated time to program formula
     *
     * <p>Note: since the type of the associated metric is a {@link
     * org.sonar.api.measures.Metric.ValueType#WORK_DUR work duration}, the
     * original formula is altered so that the obtained time, which is in
     * seconds, is again divided by 60 (since a work duration is in minutes).
     * </p>
     *
     * <p>Dependencies:</p>
     *
     * <ul>
     *     <li>{@link HalsteadMetrics#EFFORT}</li>
     * </ul>
     *
     * @see HalsteadMetrics#TIME
     */
    TIME {
        @Override
        public List<Metric> dependsUponMetrics()
        {
            return Collections.<Metric>singletonList(HalsteadMetrics.EFFORT);
        }

        @Override
        public Measure calculate(final FormulaData data,
            final FormulaContext context)
        {
            final double effort = data.getMeasure(HalsteadMetrics.EFFORT)
                .getValue();
            return new Measure(context.getTargetMetric(), effort / (18 * 60));
        }
    },

    /**
     * Estimated delivered bugs formula
     *
     * <p>Dependencies:</p>
     *
     * <ul>
     *     <li>{@link HalsteadMetrics#EFFORT}</li>
     * </ul>
     *
     * @see HalsteadMetrics#BUGS
     */
    BUGS {
        @Override
        public List<Metric> dependsUponMetrics()
        {
            return Collections.<Metric>singletonList(HalsteadMetrics.EFFORT);
        }

        @Override
        public Measure calculate(final FormulaData data,
            final FormulaContext context)
        {
            final double effort = data.getMeasure(HalsteadMetrics.EFFORT)
                .getValue();
            final double dividend = Math.pow(effort, 2.0 / 3.0);
            return new Measure(context.getTargetMetric(), dividend / 3_000.0);
        }
    },
    ;
}
