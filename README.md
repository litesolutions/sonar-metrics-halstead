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

It also provides a plugin with all metrics. And please note that it only really
provides the metrics; no dedicated widget is provided.

It requires that you use version 5.1 of the Sonar plugin API or better.

## How to use

### Build... For now, no choice

Clone the project; then, to build:

```
# The below supposes a Unix machine; if Windows, replace ./gradlew with
# gradlew.bat
./gradlew clean install
```

This will build and install the necessary jar in your local Maven installation,
and also build the plugin.

### Make available for use

Your language plugin should depend on the following artifact:

* groupId: es.litesolutions;
* artifactId: sonar-metrics-halstead;
* version: 0.1.0-SNAPSHOT.

In addition, in your SonarQube installation, you should copy the plugin (which
is named
`sonar-metrics-halstead-plugin/build/libs/sonar-metrics-halstead-plugin.jar`) in
directory `extensions/plugins` relative to your SonarQube installation.

### Compute the necessary values for provided `MetricDef`s

There are four `MetricDef`s which you should provide a value to:

* `HalsteadMetricDefs.TOTAL_OPERATORS`: the total number of operators in the
  source file;
* `HalsteadMetricDefs.DISTINCT_OPERATORS`: the number of unique operators in the
  source file;
* `HalsteadMetricDefs.TOTAL_OPERANDS`: the total number of operands in the
  source file;
* `HalsteadMetricDefs.DISTINCT_OPERANDS`: the number of unique operands in the
  source file.

Please refer to the [Wikipedia
link](https://en.wikipedia.org/wiki/Halstead_complexity_measures) for an example.

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

### Use it in your project...

Since all measures are recursively collected from files up to the project, it
means that you can drill down using these metrics.

SonarQube provides a widget by default, named `Custom Measures`, which you can
use to this effect: just select the measures you want (they all use the domain
named `Halstead`) and off you go.

## Limitations

### Potential problem with large projects when storing values

The Halstead length of a project can be pretty large (it is the sum of _all_
distinct operators and operands, over _all_ files of a project); and in some
situations, the database engine configuration of SonarQube can hit the database
limit.

This is for example the case for PostgreSQL (at least for versions 5.1.x of
SonarQube, other versions have not been tested). Should you hit the limit, you
have no choice but to modify the type of the `value` column of the
`project_measures` table by hand, to a larger type.

### No dedicated widget provided

Maybe, some day, there will be one. Contributions are of course welcome,
especially since I suck at both Ruby and anything user interface.

### Requires a modification of your language plugin...

This is probably the most annoying limitation of them all. However I see no way
around it.

