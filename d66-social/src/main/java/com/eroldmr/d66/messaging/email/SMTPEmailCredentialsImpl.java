package com.eroldmr.d66.messaging.email;

import com.eroldmr.common.configs.smtp.SMTPEmailCredentials;

/**
 * @author Mompati 'Patco' Keetile
 * @created 27-12-2022 @ 12:15
 */
public class SMTPEmailCredentialsImpl implements SMTPEmailCredentials {
  @Override
  public String username() {
    return "mompati.keetile@gmail.com";
  }

  @Override
  public String password() {
    return "dgfvpunxxcjfuxyg";
  }

  @Override
  public Boolean debug() {
    return true;
  }
}
