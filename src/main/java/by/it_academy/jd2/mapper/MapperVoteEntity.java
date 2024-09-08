package by.it_academy.jd2.mapper;

import by.it_academy.jd2.dto.InfoFromUserDTO;
import by.it_academy.jd2.entity.VoteEntity;
import by.it_academy.jd2.mapper.api.IMapperDtoToEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MapperVoteEntity implements IMapperDtoToEntity {

    private static final String OFFSET_BEL = "+03:00";

    public MapperVoteEntity() {
    }

    /**
     * Метод преобразования infoFromUserDTO в VoteEntity
     * @param infoFromUserDTO - дто с информацией от проголосованшего пользователя
     * @return VoteEntity объект для передачи в систему хранения
     */
    public VoteEntity mapFrom(InfoFromUserDTO infoFromUserDTO) {
        return VoteEntity.builder()
                .setArtist(Long.valueOf(infoFromUserDTO.getSinger()))
                .setGenres(Arrays.stream(infoFromUserDTO.getJanres()).map(Long::valueOf).collect(Collectors.toList()))
                .setInfo(infoFromUserDTO.getInfo())
                .setCreate_at(OffsetDateTime.of(infoFromUserDTO.getDateTime(), ZoneOffset.of(OFFSET_BEL)))
                .build();
    }

}
