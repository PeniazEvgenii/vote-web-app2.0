package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.service.ServiceGenre;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.storage.factory.GenreStorageFactory;

public class ServiceGenreFactory {
    private static final IGenreService instance = new ServiceGenre(GenreStorageFactory.getInstance());

    private ServiceGenreFactory() {
    }

    public static IGenreService getInstance() {
        return instance;
    }
}
