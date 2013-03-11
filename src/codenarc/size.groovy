ruleset {
    description "customized from rulesets/size.xml"

    //AbcComplexity   // DEPRECATED: Use the AbcMetric rule instead. Requires the GMetrics jar
    AbcMetric   // Requires the GMetrics jar
    ClassSize
    CrapMetric   // Requires the GMetrics jar and a Cobertura coverage file
    CyclomaticComplexity   // Requires the GMetrics jar
    MethodCount
    MethodSize
    NestedBlockDepth
}
