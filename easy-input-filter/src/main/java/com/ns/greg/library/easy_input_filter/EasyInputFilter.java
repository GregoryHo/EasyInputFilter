package com.ns.greg.library.easy_input_filter;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import static com.ns.greg.library.easy_input_filter.FilterAnnotation.DEFAULT;
import static com.ns.greg.library.easy_input_filter.FilterAnnotation.DIGIT;
import static com.ns.greg.library.easy_input_filter.FilterAnnotation.IGNORE;
import static com.ns.greg.library.easy_input_filter.FilterAnnotation.LETTER;
import static com.ns.greg.library.easy_input_filter.FilterAnnotation.SPACE;
import static com.ns.greg.library.easy_input_filter.FilterAnnotation.SPECIFIC_CHARACTER;

/**
 * Created by Gregory on 2017/5/4.
 */

public class EasyInputFilter extends InputFilter.LengthFilter {

  private static final int DEFAULT_LENGTH = 8;

  private int filterType;

  private int letterType;

  private EasyInputFilter(int max, @FilterAnnotation.FilterType int filterType, @FilterAnnotation.LetterType int letterType) {
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

      case SPACE:
        return InputFilterUtils.isSpaceCharacter(currentChar);

      case SPECIFIC_CHARACTER:
        return InputFilterUtils.isSpecificCharacter(currentChar);

      case (LETTER | DIGIT):
        return InputFilterUtils.isLetter(currentChar, letterType) || InputFilterUtils.isDigit(
            currentChar);

      case (LETTER | SPACE):
        return InputFilterUtils.isLetter(currentChar, letterType)
            || InputFilterUtils.isSpaceCharacter(currentChar);

      case (LETTER | SPECIFIC_CHARACTER):
        return InputFilterUtils.isLetter(currentChar, letterType)
            || InputFilterUtils.isSpaceCharacter(currentChar);

      case (DIGIT | SPACE):
        return InputFilterUtils.isDigit(currentChar) || InputFilterUtils.isSpaceCharacter(
            currentChar);

      case (DIGIT | SPECIFIC_CHARACTER):
        return InputFilterUtils.isDigit(currentChar) || InputFilterUtils.isSpaceCharacter(
            currentChar);

      case (LETTER | DIGIT | SPACE):
        return InputFilterUtils.isLetter(currentChar, letterType) || InputFilterUtils.isDigit(
            currentChar) || InputFilterUtils.isSpaceCharacter(currentChar);

      case (LETTER | DIGIT | SPECIFIC_CHARACTER):
        return InputFilterUtils.isAlphanumeric(currentChar);

      default:
        return false;
    }
  }

  public static final class Builder {

    private int filterLength = DEFAULT_LENGTH;

    private int filterType = DEFAULT;

    private int letterType = IGNORE;

    public Builder setMaxLength(int filterLength) {
      this.filterLength = filterLength;

      return this;
    }

    public Builder setFilterType(@FilterAnnotation.FilterType int filterType) {
      this.filterType = filterType;

      return this;
    }

    public Builder setLetterType(@FilterAnnotation.LetterType int letterType) {
      this.letterType = letterType;

      return this;
    }

    public InputFilter.LengthFilter build() {
      return new EasyInputFilter(filterLength, filterType, letterType);
    }
  }
}
