package com.example.analytics.repository.googleTrend;

import com.example.analytics.model.googleTrend.TrendData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrendDataRepository extends JpaRepository<TrendData, Long> {

}