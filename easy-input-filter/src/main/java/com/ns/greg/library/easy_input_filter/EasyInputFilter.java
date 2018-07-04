package com.ns.greg.library.easy_input_filter;

import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ns.greg.library.easy_input_filter.CharacterType.ALL;
import static com.ns.greg.library.easy_input_filter.CharacterType.ALPHANUMERIC;
import static com.ns.greg.library.easy_input_filter.CharacterType.CUSTOM;
import static com.ns.greg.library.easy_input_filter.CharacterType.DIGIT;
import static com.ns.greg.library.easy_input_filter.CharacterType.LETTER;
import static com.ns.greg.library.easy_input_filter.CharacterType.LETTER_CAPITAL;
import static com.ns.greg.library.easy_input_filter.CharacterType.LETTER_LOWERCASE;
import static com.ns.greg.library.easy_input_filter.CharacterType.SPECIAL_CHARACTER;
import static com.ns.greg.library.easy_input_filter.CharacterUtils.CAPITAL;
import static com.ns.greg.library.easy_input_filter.CharacterUtils.IGNORE;
import static com.ns.greg.library.easy_input_filter.CharacterUtils.LOWERCASE;

/**
 * @author Gregory
 * @since 2017/5/4
 */
public class EasyInputFilter extends InputFilter.LengthFilter {

  private final CharacterType acceptorType;
  private final char[] customAcceptedCharacters;
  private final CharacterType filterType;
  private final char[] customFilteredCharacters;

  private EasyInputFilter(int maxLength, CharacterType acceptorType,
      char[] customAcceptedCharacters, CharacterType filterType,
      char[] customFilteredCharacters) {
    super(maxLength);
    this.acceptorType = acceptorType;
    this.customAcceptedCharacters = customAcceptedCharacters;
    this.filterType = filterType;
    this.customFilteredCharacters = customFilteredCharacters;
  }

  @Override
  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {
    CharSequence lengthSource = super.filter(source, start, end, dest, dstart, dend);
     if (lengthSource == null) {
       if (source instanceof SpannableStringBuilder) {
         SpannableStringBuilder builder = (SpannableStringBuilder) source;
         for (int i = end - 1; i >= start; i--) {
           char c = source.charAt(i);
           if (!accepted(c) || filtered(c)) {
             builder.delete(i, i + 1);
           }
         }

         return source;
       } else {
         StringBuilder builder = new StringBuilder();
         for (int i = start; i < end; i++) {
           char c = source.charAt(i);
           if (accepted(c) && !filtered(c)) {
             builder.append(c);
           }
         }

         return builder.toString();
       }
     }

    return lengthSource;
  }

  private boolean accepted(char c) {
    switch (acceptorType) {
      default:
      case ALL:
        return true;

      case CUSTOM:
        for (char accepted : customAcceptedCharacters) {
          if (accepted == c) {
            return true;
          }
        }

        return false;

      case LETTER_LOWERCASE:
        return CharacterUtils.isLetter(c, LOWERCASE);

      case LETTER_CAPITAL:
        return CharacterUtils.isLetter(c, CAPITAL);

      case LETTER:
        return CharacterUtils.isLetter(c, IGNORE);

      case DIGIT:
        return CharacterUtils.isDigit(c);

      case SPECIAL_CHARACTER:
        return CharacterUtils.isSpecificCharacter(c);

      case ALPHANUMERIC:
        return CharacterUtils.isAlphanumeric(c);
    }
  }

  private boolean filtered(char c) {
    if (filterType == null) {
      // Means nothing needs to  be filtered
      return false;
    }

    switch (filterType) {
      default:
      case ALL:
        return true;

      case CUSTOM:
        for (char filtered : customFilteredCharacters) {
          if (filtered == c) {
            return true;
          }
        }

        return false;

      case LETTER_LOWERCASE:
        return CharacterUtils.isLetter(c, LOWERCASE);

      case LETTER_CAPITAL:
        return CharacterUtils.isLetter(c, CAPITAL);

      case LETTER:
        return CharacterUtils.isLetter(c, IGNORE);

      case DIGIT:
        return CharacterUtils.isDigit(c);

      case SPECIAL_CHARACTER:
        return CharacterUtils.isSpecificCharacter(c);

      case ALPHANUMERIC:
        return CharacterUtils.isAlphanumeric(c);
    }
  }

  public static final class Builder {

    private static final int DEFAULT_LENGTH = 8;

    private int maxLength;
    private CharacterType acceptorType;
    private char[] customAcceptedCharacters;
    private CharacterType filterType;
    private char[] customFilteredCharacters;

    public Builder() {
      maxLength = DEFAULT_LENGTH;
      acceptorType = ALL;
      filterType = null;
    }

    public Builder setMaxLength(int filterLength) {
      this.maxLength = filterLength;
      return this;
    }

    public Builder setAcceptorType(@NonNull CharacterType acceptorType) {
      this.acceptorType = acceptorType;
      return this;
    }

    public Builder setCustomAcceptedCharacters(char... customAcceptedCharacters) {
      this.customAcceptedCharacters = customAcceptedCharacters;
      return this;
    }

    public Builder setFilterType(CharacterType filterType) {
      this.filterType = filterType;
      return this;
    }

    public Builder setCustomFilteredCharacters(char[] customFilteredCharacters) {
      this.customFilteredCharacters = customFilteredCharacters;
      return this;
    }

    public EasyInputFilter build() {
      if (acceptorType == CUSTOM) {
        if (customAcceptedCharacters == null || customAcceptedCharacters.length == 0) {
          throw new IllegalArgumentException(
              "You assigned acceptor type as [CUSTOM], but you assigned null or empty custom accepted character array.");
        }
      } else {
        if (customAcceptedCharacters != null) {
          throw new IllegalArgumentException(
              "You assigned custom accepted character array, but the acceptor type is not [CUSTOM].");
        }
      }

      if (filterType == CUSTOM) {
        if (customFilteredCharacters == null || customFilteredCharacters.length == 0) {
          throw new IllegalArgumentException(
              "You assigned acceptor type as [CUSTOM], but you assigned null or empty custom filtered character array.");
        }
      } else {
        if (customFilteredCharacters != null) {
          throw new IllegalArgumentException(
              "You assigned custom filtered character array, but the filter type is not [CUSTOM].");
        }
      }

      return new EasyInputFilter(maxLength, acceptorType, customAcceptedCharacters, filterType,
          customFilteredCharacters);
    }
  }
}
