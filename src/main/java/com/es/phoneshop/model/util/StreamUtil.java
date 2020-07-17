package com.es.phoneshop.model.util;

import com.es.phoneshop.model.entity.Product;

import java.util.LinkedList;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtil {
    private StreamUtil() {

    }

    public static Stream<Product> reverseStream(Stream<Product> stream) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(stream
                .collect(Collectors.toCollection(LinkedList::new))
                .descendingIterator(), Spliterator.ORDERED), false);
    }
}
