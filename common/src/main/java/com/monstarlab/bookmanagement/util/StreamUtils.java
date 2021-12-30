package com.monstarlab.bookmanagement.util;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@UtilityClass
public class StreamUtils {

    public static <T> Optional<T> find(Stream<T> stream, Predicate<T> predicate){
        return stream
                .filter(predicate)
                .findFirst();
    }
}
