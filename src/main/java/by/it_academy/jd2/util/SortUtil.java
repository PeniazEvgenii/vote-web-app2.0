package by.it_academy.jd2.util;

import by.it_academy.jd2.dto.TextAndTimeVote;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public final class SortUtil {
    private SortUtil() {}

    public static  Map<Long, Long> sort(Map<Long, Long> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static List<TextAndTimeVote> sortListByTime(List<TextAndTimeVote> list) {
        return list.stream()
                .sorted(Comparator.comparing(TextAndTimeVote::getTime).reversed())
                .collect(Collectors.toList());
    }


}
