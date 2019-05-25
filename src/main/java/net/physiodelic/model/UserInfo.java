package net.physiodelic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by joris on 22/04/17.
 * Simple pojo to hold user information
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
  private static final long serialVersionUID = -1650463058907821996L;
  private String firstName;
  private String lastName;
  private Date DOB;
  private Address homeAddress;
}

// That's All Folks !!
