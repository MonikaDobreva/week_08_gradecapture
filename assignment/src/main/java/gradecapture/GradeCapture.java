package gradecapture;

import java.util.AbstractMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The regex defines two parenthesis groups, one for the student number, one for
 * the grade. 
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class GradeCapture {
    static final String REGEX = "^(\\d{7})" // starting with a number (student number = 7 digits)
            + ".*" // then whatever (name of student)
            + "\\s+" // at least one whitespace
            + "(10(\\.0)?|\\d([.,]\\d)?)" // 10(.0)? or one digit and optionally comma or period followed by one digit.
            + "$"; // and end
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private final Matcher matcher;

    /**
     * Construct a GradeCapture from a string (line).
     *
     * @param line to read
     */
    public GradeCapture(String line) {
        matcher = PATTERN.matcher(line);
    }

    /**
     * Create a tuple. Use AbstractMap. SimpleEntry as implementing class.
     *
     * @return the tuple.
     */
    public AbstractMap.SimpleEntry<Integer, Double> getResult() {
        return new AbstractMap.SimpleEntry<>(getStudentNumber(), getGrade());
    }

    /**
     * Does the line contain the required data?
     *
     * @return whether there is a match
     */
    public boolean hasResult() {
        return matcher.matches();
    }

    /**
     * Get the grade, if any. Make sure to replace ','(comma) by . (period),
     * before you try to get the double value of the string.
     *
     * @return the grade or null when no match
     */
    public Double getGrade() {
        String check = "\\s+(10(\\.0)?|\\d([.,]\\d)?)$";
        Pattern checkGrade = Pattern.compile(check);

        if(this.hasResult()){
            String match = matcher.group(0); //the whole matches
            Matcher m = checkGrade.matcher(match);

            if (m.find()){
                String result = m.group(0);
                if(result.contains(",")){
                    result = result.replace(",", ".");
                }
                return Double.parseDouble(result);
            }
        }

        return null;
    }

    /**
     * Get the student number, if any.
     *
     * @return the student number or null when no match.
     */
    public Integer getStudentNumber() {
        String check = "^(\\d{7})";
        Pattern checkNumber = Pattern.compile(check);

        if(this.hasResult()){
            String match = matcher.group(0);
            Matcher m = checkNumber.matcher(match);

            if (m.find()){
                String result = m.group(0);
                return Integer.parseInt(result);
            }
        }

        return null;
    }
    
    
}
