package com.eroldmr.d66.constant;

import lombok.AllArgsConstructor;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 12:23
 */
@AllArgsConstructor
public enum VoteType {
  UP_VOTE(1),
  DOWN_VOTE(-1);
  private final Integer direction;
}
