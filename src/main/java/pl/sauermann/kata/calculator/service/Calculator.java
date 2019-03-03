package pl.sauermann.kata.calculator.service;

import org.apache.logging.log4j.util.Strings;
import pl.sauermann.kata.calculator.exceptions.NegativeProhibitedException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Calculator {

    public static int adding(String input) throws RuntimeException{
        if (Strings.isBlank(input)){
            return 0;
        }
        String splitter  = "[,\n/";
        String inputWithoutSplitters = input.trim();
        if (input.startsWith("//")) {
            String[] split = input.split("\n", 2);
            inputWithoutSplitters = split[1];
            splitter = customSplitterGenerator(splitter, split[0]);
        }
        splitter = splitter +  "]";
        List<Integer> argumentList = getArgumentsList(inputWithoutSplitters, splitter);
        illegalArgumentsValidator(argumentList);
        return argumentList.stream()
                .flatMapToInt(IntStream::of)
                .sum();
    }

    private static List<Integer> getArgumentsList(String input, String regex) {
        return Stream.of(input)
                    .flatMap(string -> Arrays.stream(string.split(regex)))
                    .filter(s -> !Strings.isBlank(s))
                    .flatMapToInt(s -> IntStream.builder()
                            .add(Integer.valueOf(s.trim()))
                            .build())
                    .filter(value -> value <= 1000)
                    .boxed()
                    .collect(Collectors.toList());
    }

    private static String customSplitterGenerator(String splitter, String s) {
        String regexInside = "(^.*?\\[|\\]\\s*$)", regexBetween = "\\]\\[";
        String[] arrayOfSplitters = s.substring(2).replaceAll(regexInside,"").split(regexBetween);
        for (String arrayOfSplitter : arrayOfSplitters) {
            splitter = splitter +"]|[" + arrayOfSplitter;
        }
        return splitter;
    }


    private static void illegalArgumentsValidator(List<Integer> collect) {
        List<Integer> negativeValues = collect.stream()
                .filter(integer -> integer < 0)
                .collect(Collectors.toList());
        if(negativeValues.size() > 0 ){
            throw new NegativeProhibitedException("Ujemne argumenty niedozwolone: " + negativeValues);
        }
    }

}
