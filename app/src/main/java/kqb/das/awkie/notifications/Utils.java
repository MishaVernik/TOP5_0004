package kqb.das.awkie.notifications;

public class Utils {

    public static String removeTillWord(String input, String word) {
        return input.substring(input.indexOf(word));
    }

    public static String removeAfterWord(String input, String word) {
        return input.substring(0, input.indexOf(word));
    }
}
