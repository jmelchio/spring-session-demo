package net.physiodelic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by joris on 27/03/17. Simple model for storing key value pairs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionItem {
  private String key;
  private Object value;
}
