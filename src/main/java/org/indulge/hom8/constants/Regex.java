package org.indulge.hom8.constants;

public interface Regex {
    String SQUARE_BRACKETS = "[\\[\\]]";
    String VALID_PIN = "^(?!.*(.).*\\1)\\d{4}$";
}
