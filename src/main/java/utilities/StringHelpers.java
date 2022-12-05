package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelpers {
    public static String getCurrentDateTimeString() {
        String dateTimePattern = "yyyyMMdd-HHmmssSSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimePattern);
        return simpleDateFormat.format(new Date());
    }

    public static String getCurrentUTCDateTimeString() {
        return Instant.now().toString();
    }

    public static String getCurrentLocalDateTime(String expectedFormat) {
        return LocalDateTime.now().plusDays(0).format(DateTimeFormatter.ofPattern(expectedFormat));
    }

    public static String findTextWithinStringViaRegex(String allText, String regexExpression) {
        String foundString = "";
        Pattern pattern = Pattern.compile(regexExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(allText);
        if (matcher.find()) {
            foundString = matcher.group(1);
        }
        return foundString;
    }

    public static String findTextWithinStringViaRegex(String allText, String regexExpression, Integer groupToReturn) {
        String foundString = "";
        Pattern pattern = Pattern.compile(regexExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(allText);
        if (matcher.find()) {
            foundString = matcher.group(groupToReturn);
        }
        return foundString;
    }

    public static String convertStringDateToFormat(String dateString, String sourceFormat, String expectedFormat) {
        DateFormat sourceFormatting = new SimpleDateFormat(sourceFormat);
        DateFormat expectedFormatting = new SimpleDateFormat(expectedFormat);
        String stringDateOutput = "";
        try {
            Date date = sourceFormatting.parse(dateString);
            stringDateOutput = expectedFormatting.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return stringDateOutput;
    }

    public static String generateRandomNumberString(int min, int max, String format) {
        return String.format(format, (int) Math.floor(Math.random() * (max - min + 1) + min));
    }

    public static String[] splitStringByDelimiter(String inputString, String delimiter) {
        return inputString.split(delimiter);
    }
}
