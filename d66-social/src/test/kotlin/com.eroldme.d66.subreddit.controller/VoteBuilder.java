package com.eroldme.d66.subreddit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class VoteBuilder {
  public static List<String> getIds() {
    return Collections.singletonList("1");
  }

  public static VoteDto getDto() {
    VoteDto dto = new VoteDto();
    dto.setId("1");
    return dto;
  }
}