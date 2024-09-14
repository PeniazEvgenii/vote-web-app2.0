package by.it_academy.jd2.dto;

import java.util.List;
import java.util.Map;

public class SortedDateDTO {
    private final List<TextTimeString> textTimeString;
    private final Map<Long, Long> sortSing;
    private final Map<Long, Long> sortJanr;


    public SortedDateDTO( Map<Long, Long> sortSing, Map<Long, Long> sortJanr, List<TextTimeString> textTimeString) {
        this.textTimeString = textTimeString;
        this.sortSing = sortSing;
        this.sortJanr = sortJanr;
    }

    public List<TextTimeString> getTextAndTimeVotes() {
        return textTimeString;
    }

    public Map<Long, Long> getSortSing() {
        return sortSing;
    }

    public Map<Long, Long> getSortJanr() {
        return sortJanr;
    }
}
