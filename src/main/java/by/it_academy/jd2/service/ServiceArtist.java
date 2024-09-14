package by.it_academy.jd2.service;

import by.it_academy.jd2.entity.Artist;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.storage.api.IStorage;

import java.util.Map;
import java.util.stream.Collectors;

public class ServiceArtist implements IArtistService {

    private final IStorage<Artist> artistStorage;

    public ServiceArtist(IStorage<Artist> artistStorage) {
        this.artistStorage = artistStorage;
    }

    @Override
    public Long create(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException();
        }
        return artistStorage.create(new Artist(name));

    }

    @Override
    public String get(Long id) {
        Artist artist = artistStorage.get(id);
        return artist.getName();
    }

    @Override
    public Map<Long, String> getAll() {
        Map<Long, Artist> longArtistMap = artistStorage.getAll();
        return longArtistMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getName()));
    }

    @Override
    public boolean delete(String deleteArtist) {

        return artistStorage.delete(Long.valueOf(deleteArtist));

    }
}
