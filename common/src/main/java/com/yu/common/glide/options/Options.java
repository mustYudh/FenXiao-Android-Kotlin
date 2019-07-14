package com.yu.common.glide.options;

import java.io.Serializable;

/**
 * @author chenwei
 * @date 2018/3/21
 */
public class Options implements Serializable {

  private boolean fadeIn;
  private int defaultImageResource;
  private boolean circle;

  public Options setFadeIn(boolean fadeIn) {
    this.fadeIn = fadeIn;
    return this;
  }

  public boolean isFadeIn() {
    return fadeIn;
  }

  public Options setDefaultImageResource(int defaultImageResource) {
    this.defaultImageResource = defaultImageResource;
    return this;
  }

  public int getDefaultImageResource() {
    return defaultImageResource;
  }

  public boolean isCircle() {
    return circle;
  }

  public Options setCircle(boolean circle) {
    this.circle = circle;
    return this;
  }
}
