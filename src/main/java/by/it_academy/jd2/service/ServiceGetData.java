package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.SortedDateDTO;
import by.it_academy.jd2.dto.TextTimeString;
import by.it_academy.jd2.dto.TextAndTimeVote;
import by.it_academy.jd2.entity.VoteEntity;
import by.it_academy.jd2.service.api.IServiceGetData;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.util.SortUtil;
import by.it_academy.jd2.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceGetData implements IServiceGetData {
    private final IStorage<VoteEntity> voteStorage;

    private static final String FORMAT_DATE = "HH:mm:ss _ dd.MM.yyyy";
    private static final String TIME_ZONE = "Europe/Minsk";

    public ServiceGetData(IStorage<VoteEntity> voteStorage){
        this.voteStorage = voteStorage;
    }

    /**
     * Метод получения посчитанной и отсортированной информации по голосованию
     * @return SortedDateDTO - объект с отсортированной информацией по голосованию
     */
    @Override
    public SortedDateDTO getData() {

        Map<Long, VoteEntity> votes = voteStorage.getAll();

        return countAndSortVote(votes);
    }


    /**
     * Метод для подсчета информации о голосовании и сортировки полученных данных
     * @param votes голоса с информацией, полученные из хранилищи
     * @return SortedDateDTO - объект с отсортированной информацией по голосованию
     */
    private SortedDateDTO countAndSortVote(Map<Long, VoteEntity> votes) {
        List<VoteEntity> listVote = new ArrayList<>(votes.values());

        Map<Long, Long> artistCount = new HashMap<>();
        Map<Long, Long> genresCount = new HashMap<>();
        List<TextAndTimeVote> listTextTime = new ArrayList<>();

        for (VoteEntity vote : listVote) {
            artistCount.merge(vote.getArtistId(), 1L, Long::sum);
            for (Long genre : vote.getGenresId()) {
                genresCount.merge(genre, 1L, Long::sum);
            }
            String info = vote.getInfo();
            LocalDateTime localDateTime = TimeUtil.convertOffsetToLocalDateTime(vote.getCreate_at(), TIME_ZONE);
            listTextTime.add(new TextAndTimeVote(info, localDateTime));
        }

        List<TextAndTimeVote> textAndTimeVotes = SortUtil.sortListByTime(listTextTime);

        return new SortedDateDTO(SortUtil.sort(artistCount),
                SortUtil.sort(genresCount),
                getListWithFormatTime(textAndTimeVotes));
    }

    /**
     * Метод для преобразования списка, хранящего объект TextAndTimeVote в список с объектом TextTimeString
     * TextAndTimeVote хранит текстовую информацию от пользователя, а также временя и дату в LocalDateTime
     * TextTimeString хранит текстовую информацию от пользователя и строковое представление времени и даты
     * @param textAndTimeVotes  лист объектов TextAndTimeVote из хранилища
     * @return List<TextTimeString> лист объектов с преобразованными датой и временем
     */
    private List<TextTimeString> getListWithFormatTime(List<TextAndTimeVote> textAndTimeVotes) {
        List<TextTimeString>  result = new ArrayList<>();
        for (TextAndTimeVote timeVote : textAndTimeVotes) {
            LocalDateTime time = timeVote.getTime();
            TextTimeString textTimeString = new TextTimeString(timeVote.getTextInfo(),
                                                               time.format(DateTimeFormatter.ofPattern(FORMAT_DATE)));

            result.add(textTimeString);
        }
        return result;
    }
}
