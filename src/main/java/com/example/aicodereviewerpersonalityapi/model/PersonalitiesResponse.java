package com.example.aicodereviewerpersonalityapi.model;

import java.util.List;

public class PersonalitiesResponse {
  private List<PersonalityItem> items;
  private String traceId;

  public PersonalitiesResponse(List<PersonalityItem> items, String traceId) {
    this.items = items;
    this.traceId = traceId;
  }

  public List<PersonalityItem> getItems() {
    return items;
  }

  public String getTraceId() {
    return traceId;
  }

  public static class PersonalityItem {
    private String name;
    private String description;

    public PersonalityItem(String name, String description) {
      this.name = name;
      this.description = description;
    }

    public String getName() {
      return name;
    }

    public String getDescription() {
      return description;
    }
  }
}

