package com.nhbs.fenxiao.utils.oss;

import java.io.Serializable;

public class PersistenceResponse implements Serializable {
  public boolean success;
  public String fileAbsPath;
  public String cloudUrl;

  @Override public String toString() {
    return "PersistenceResponse{"
        + "success="
        + success
        + ", fileAbsPath='"
        + fileAbsPath
        + '\''
        + ", cloudUrl='"
        + cloudUrl
        + '\''
        + '}';
  }
}