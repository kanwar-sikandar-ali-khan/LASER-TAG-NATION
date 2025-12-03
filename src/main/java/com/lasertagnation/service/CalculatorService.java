package com.lasertagnation.service;

import com.lasertagnation.dto.Calculator;

import java.util.Map;

public interface CalculatorService
{
    public Map<String, Double> CalculateMoq(Calculator calculator);
}
