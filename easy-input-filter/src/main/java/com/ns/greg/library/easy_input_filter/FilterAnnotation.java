package com.ns.greg.library.easy_input_filter;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Gregory
 * @since 2017/8/10
 */
public class FilterAnnotation {

  public static final int DEFAULT = 0;

  public static final int LETTER = 1;

  public static final int DIGIT = 1 << 1;

  public static final int SPACE = 1 << 2;

  public static final int SPECIFIC_CHARACTER = 1 << 3;

  public static final int ALPHANUMERIC = LETTER | DIGIT | SPECIFIC_CHARACTER;

  @IntDef(flag = true, value = { DEFAULT, LETTER, DIGIT, SPACE, SPECIFIC_CHARACTER, ALPHANUMERIC })
  @Retention(RetentionPolicy.SOURCE) @interface FilterType {

  }

  public static final int IGNORE = 0;

  public static final int LOWERCASE = 1;

  public static final int CAPITAL = 2;

  @IntDef({ IGNORE, LOWERCASE, CAPITAL }) @Retention(RetentionPolicy.SOURCE) @interface LetterType {

  }
}
