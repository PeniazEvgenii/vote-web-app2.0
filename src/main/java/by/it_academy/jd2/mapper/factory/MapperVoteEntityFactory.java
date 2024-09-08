package by.it_academy.jd2.mapper.factory;

import by.it_academy.jd2.mapper.MapperVoteEntity;
import by.it_academy.jd2.mapper.api.IMapperDtoToEntity;

public class MapperVoteEntityFactory {
    private static final IMapperDtoToEntity instance = new MapperVoteEntity();

    private MapperVoteEntityFactory() {}

    public static IMapperDtoToEntity getInstance() {
        return instance;
    }
}
