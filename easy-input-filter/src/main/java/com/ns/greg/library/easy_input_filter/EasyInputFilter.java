package com.ns.greg.library.easy_input_filter;

import android.support.annotation.IntDef;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Gregory on 2017/5/4.
 */

public class EasyInputFilter extends InputFilter.LengthFilter {

  private static final int DEFAULT_LENGTH = 8;

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

  private int filterType;

  private int letterType;

  private EasyInputFilter(int max, @FilterType int filterType, @LetterType int letterType) {
    super(max);
    this.filterType = filterType;
    this.letterType = letterType;
  }

  @Override
  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {
    CharSequence lengthSource = super.filter(source, start, end, dest, dstart, dend);
    if (lengthSource == null) {
      if (source instanceof SpannableStringBuilder) {
        SpannableStringBuilder sourceAsSpannableBuilder = (SpannableStringBuilder) source;
        for (int i = end - 1; i >= start; i--) {
          char currentChar = source.charAt(i);
          if (!checkFilter(currentChar)) {
            sourceAsSpannableBuilder.delete(i, i + 1);
          }
        }

        return source;
      } else {
        StringBuilder filteredStringBuilder = new StringBuilder();
        for (int i = start; i < end; i++) {
          char currentChar = source.charAt(i);
          if (checkFilter(currentChar)) {
            filteredStringBuilder.append(currentChar);
          }
        }

        return filteredStringBuilder.toString();
      }
    }

    return lengthSource;
  }

  private boolean checkFilter(char currentChar) {
    switch (filterType) {
      case DEFAULT:
        return true;

      case LETTER:
        return InputFilterUtils.isLetter(currentChar, letterType);

      case DIGIT:
        return InputFilterUtils.isDigit(currentChar);

      case (LETTER | DIGIT):
        return InputFilterUtils.isLetter(currentChar, letterType) || InputFilterUtils.isDigit(
            currentChar);

      case (LETTER | DIGIT | SPACE):
        return InputFilterUtils.isLetter(currentChar, letterType) || InputFilterUtils.isDigit(
            currentChar) || InputFilterUtils.isSpaceCharacter(currentChar);

      case SPECIFIC_CHARACTER:
        return InputFilterUtils.isSpecificCharacter(currentChar);

      case ALPHANUMERIC:
        return InputFilterUtils.isAlphanumeric(currentChar);

      default:
        return false;
    }
  }

  public static final class Builder {

    private int filterLength = DEFAULT_LENGTH;

    private int filterType = DEFAULT;

    private int letterType = IGNORE;

    public Builder() {

    }

    public Builder setMaxLength(int filterLength) {
      this.filterLength = filterLength;

      return this;
    }

    public Builder setFilterType(@FilterType int filterType) {
      this.filterType = filterType;

      return this;
    }

    public Builder setLetterType(@LetterType int letterType) {
      this.letterType = letterType;

      return this;
    }

    public InputFilter.LengthFilter build() {
      return new EasyInputFilter(filterLength, filterType, letterType);
    }
  }
}
