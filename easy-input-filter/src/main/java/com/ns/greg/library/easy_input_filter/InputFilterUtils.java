package com.ns.greg.library.easy_input_filter;

import static com.ns.greg.library.easy_input_filter.EasyInputFilter.CAPITAL;
import static com.ns.greg.library.easy_input_filter.EasyInputFilter.IGNORE;
import static com.ns.greg.library.easy_input_filter.EasyInputFilter.LOWERCASE;

/**
 * Created by Gregory on 2017/5/4.
 */

public class InputFilterUtils {

  static boolean isLetter(char c, @EasyInputFilter.LetterType int checkType) {
    int code = (int) c;

    switch (checkType) {
      case IGNORE:
        return ('A' <= code && code <= 'Z') || ('a' <= code && code <= 'z');

      case LOWERCASE:
        return 'a' <= code && code <= 'z';

      case CAPITAL:
        return 'A' <= code && code <= 'Z';

      default:
        return false;
    }
  }

  static boolean isDigit(char c) {
    int code = (int) c;

    return '0' <= code && code <= '9';
  }

  static boolean isSpecificCharacter(char c) {
    int code = (int) c;

    // According to ascii table
    return (32 <= code && code <= 47) || (58 <= code && code <= 64
        || (91 <= code && code <= 96)
        || (123 <= code && code <= 126));
  }

  static boolean isSpaceCharacter(char c) {
    int code = (int) c;

    return code == 32;
  }

  static boolean isAlphanumeric(char c) {

    return isLetter(c, IGNORE) || isDigit(c) || isSpecificCharacter(c);
  }

  public static int getType(char c) {
    if (isLetter(c, IGNORE)) {
      return EasyInputFilter.LETTER;
    }

    if (isDigit(c)) {
      return EasyInputFilter.DIGIT;
    }

    if (isSpecificCharacter(c)) {
      return EasyInputFilter.SPECIFIC_CHARACTER;
    }

    return 0x0F;
  }

  public static boolean isChinese(char c) {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

    return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
        || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
        || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
        || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
        || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
  }
}
