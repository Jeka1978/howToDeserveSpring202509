package com.borisov.howtodeservespring.infra;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class PredestroyFailAnalyser extends AbstractFailureAnalyzer<FailException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, FailException cause) {
        return new FailureAnalysis("НЕТ PreDestroy в Prototype!", "Удали аннотацию!", cause);
    }
}
