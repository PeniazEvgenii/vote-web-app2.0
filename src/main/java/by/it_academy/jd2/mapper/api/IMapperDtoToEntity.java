package by.it_academy.jd2.mapper.api;

import by.it_academy.jd2.dto.InfoFromUserDTO;
import by.it_academy.jd2.entity.VoteEntity;

public interface IMapperDtoToEntity {

    VoteEntity mapFrom(InfoFromUserDTO infoFromUserDTO);
}
