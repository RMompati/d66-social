package com.eroldmr.d66.messaging.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 14:57
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class NotificationEmail {
  private String subject;
  private String recipient;
  private String body;
}
