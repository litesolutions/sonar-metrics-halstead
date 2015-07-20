package es.litesolutions.sonar.metrics.halstead;

import org.sonar.api.measures.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
@ParametersAreNonnullByDefault
public enum HalsteadFormula
    implements Formula
{
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
            // A metric value type of WORK_DUR is in minutes; we therefore
            // divide the original Halstead formula (which gives a time in
            // seconds) by 60 again
            return new Measure(context.getTargetMetric(), effort / (18 * 60));
        }
    },
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
