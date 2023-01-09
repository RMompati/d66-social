package com.eroldmr.d66.refreshtoken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author Mompati 'Patco' Keetile
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {
  @Id
  @SequenceGenerator(name = "token_id_seq", sequenceName = "token_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "token_id_seq", strategy = GenerationType.IDENTITY)
  private Long id;
  private String token;
  private Instant createdAt;
}
