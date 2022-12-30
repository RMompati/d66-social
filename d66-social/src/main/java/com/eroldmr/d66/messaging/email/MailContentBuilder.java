package com.eroldmr.d66.messaging.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 14:52
 */
@AllArgsConstructor
@Service
public class MailContentBuilder {
  private final TemplateEngine templateEngine;

  public String build(String name, String link) {
    Context context = new Context();
    context.setVariable("name", name);
    context.setVariable("link", link);
    return templateEngine.process("mailTemplate", context);
  }
}
