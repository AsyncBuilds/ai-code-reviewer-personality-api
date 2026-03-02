package com.example.aicodereviewerpersonalityapi.config;

import java.util.UUID;
import org.slf4j.MDC;

public final class TraceIdUtil {

  private TraceIdUtil() {}

  public static String currentTraceId() {
    String traceId = MDC.get(TraceIdFilter.TRACE_ID);
    return traceId == null ? UUID.randomUUID().toString() : traceId;
  }
}

