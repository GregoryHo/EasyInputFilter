package com.ns.greg.library.easy_input_filter;

/**
 * @author gregho
 * @since 2018/7/2
 */
public enum CharacterType {

  /**
   * Accepted all characters
   */
  ALL,

  /**
   * Custom characters
   */
  CUSTOM,

  /**
   * Letter only include lowercase
   */
  LETTER_LOWERCASE,

  /**
   * Letter only include capital
   */
  LETTER_CAPITAL,

  /**
   * Letter include lowercase can capital
   */
  LETTER,

  /**
   * Digit numbers
   */
  DIGIT,

  /**
   * Special characters
   */
  SPECIAL_CHARACTER,

  /**
   * Letter, digit and special characters
   */
  ALPHANUMERIC
}
