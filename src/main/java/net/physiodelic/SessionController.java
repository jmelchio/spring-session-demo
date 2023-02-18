package net.physiodelic;

import javax.servlet.http.HttpSession;
import net.physiodelic.model.SessionItem;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by joris on 27/03/17.
 * Simple controller that allows CRUD operations on session
 */
@RestController
public class SessionController {
  private final static Logger logger = Logger.getLogger(SessionController.class.getName());

  @RequestMapping(name = "/session", method = RequestMethod.GET)
  public List<SessionItem> sessionGet(HttpSession session, @RequestParam(value = "key", required = false) String key) {
    logger.log(Level.INFO, "sessionGet request received.");
    List<SessionItem> itemList = new ArrayList<>();
    if(key == null) {
      for(Enumeration<String> keys = session.getAttributeNames(); keys.hasMoreElements();) {
        String aKey = keys.nextElement();
        itemList.add(new SessionItem(aKey, session.getAttribute(aKey)));
      }
    } else {
      itemList.add(new SessionItem(key, session.getAttribute(key)));
    }
    return itemList;
  }

  @RequestMapping(name = "/session", method = RequestMethod.POST)
  public SessionItem sessionPost(HttpSession session, @RequestBody()SessionItem sessionItem) {
    logger.log(Level.INFO, "sessionPost request received.");
    session.setAttribute(sessionItem.getKey(), sessionItem.getValue());
    return sessionItem;
  }

  @RequestMapping(name = "/session", method = RequestMethod.DELETE)
  public SessionItem sessionDelete(HttpSession session, @RequestParam(value = "key") String key) {
    logger.log(Level.INFO, "sessionDelete request received.");
    session.removeAttribute(key);
    return new SessionItem(key, "deleted");
  }
}
