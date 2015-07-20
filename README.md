## Readme first

This package is licensed under the Lesser General Public License, version 3 or
later.

This package requires **Java 7**.

## Versions

None yet!

## What this is

This package provides Sonar metrics allowing you to compute [Halstead complexity
measures](https://en.wikipedia.org/wiki/Halstead_complexity_measures). It is
meant to be used in language plugins.

Note that it only really provides the metrics; no dedicated widget is provided.

It requires that you use version 5.1 of the Sonar plugin API or better.

## How to use

### Compute the necessary values for provided `MetricDef`s

Those 4 `MetricDef`s are:

* `HalsteadMetricDefs.TOTAL_OPERATORS`: the total number of operators in the
  source file;
* `HalsteadMetricDefs.DISTINCT_OPERATORS`: the number of unique operators in the
  source file;
* `HalsteadMetricDefs.TOTAL_OPERANDS`: the total number of operands in the
  source file;
* `HalsteadMetricDefs.DISTINCT_OPERANDS`: the number of unique operands in the
  source file.

Please refer to the link above for an example.

One way to do this is to write a `SquidAstVisitor` and to override the
`.leaveFile()` method. Don't forget to add this visitor to your `AstScanner`, of
course!

Recall that if the file has failed to parse, the `AstNode` given as an argument
to `.leaveFile()` will be null. The metric calculations account for such a case,
so no special handling is needed.

### Save the measures

Typically, this is done in your `Sensor` implementation (after the scanning is
complete, obviously). For any `HalsteadMetricDef.XXX`, there exists a matching
`HalsteadMetric.XXX` on which you want to save the measure (this is the method
`.saveMeasure()` of the `SensorContext`).

