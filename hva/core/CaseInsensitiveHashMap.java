package hva.core;

import java.util.HashMap;

public class CaseInsensitiveHashMap<V> extends HashMap<String, V> {

  @Override
  public V put(String key, V value) {
    return super.put(key.toLowerCase(), value);
  }

  @Override
  public V get(Object key) {
    if (key instanceof String) {
      return super.get(((String) key).toLowerCase());
    }
    return null;
  }

  @Override
  public boolean containsKey(Object key) {
    if (key instanceof String) {
      return super.containsKey(((String) key).toLowerCase());
    }
    return false;
  }

  @Override
  public V remove(Object key) {
    if (key instanceof String) {
      return super.remove(((String) key).toLowerCase());
    }
    return null;
  }
}
