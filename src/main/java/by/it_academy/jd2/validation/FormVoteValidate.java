package by.it_academy.jd2.validation;

import by.it_academy.jd2.dto.InfoFromUserDTO;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.validation.api.IValidateFormInfo;


public class FormVoteValidate implements IValidateFormInfo {
    private final IArtistService artistService;
    private final IGenreService genreService;

    private static final int MIN_COUNT_GENRES = 3;

    public FormVoteValidate(IArtistService artistService, IGenreService genreService) {
        this.artistService = artistService;
        this.genreService = genreService;
    }

    /**
     * Метод для валидации информации, полученной от пользователя
     *
     * @param infoFromUserDTO - объект InfoFromUserDTO с информацией
     * @return объект ValidationResult, включающий лист ошибок List<Err> errors
     */
    @Override
    public ValidationResult valid(InfoFromUserDTO infoFromUserDTO) {
        ValidationResult validationResult = new ValidationResult();

        if (infoFromUserDTO.getSinger() == null) {
            validationResult.addError(new Err("singer_incorrect", "Singer is not chosen", "Не выбран исполнитель"));
        } else if (artistService.get(Long.valueOf(infoFromUserDTO.getSinger())) == null) {
            validationResult.addError(new Err("singer_incorrect", "Исполнитель не существует"));
            return validationResult;
        }

        String[] genres = infoFromUserDTO.getJanres();
        if (genres == null || genres.length < MIN_COUNT_GENRES) {
            validationResult.addError(new Err("genres_incorrect", "You need to choose " + MIN_COUNT_GENRES + " or more genres",
                    "Необходимо выбрать " + MIN_COUNT_GENRES + " или более жанра"));
        } else {
            for (String genre : genres) {
                if (genreService.get(Long.valueOf(genre)) == null) {
                    validationResult.addError(new Err("genres_incorrect", "Жанр не существует"));
                    return validationResult;
                }
            }
        }
        if (infoFromUserDTO.getInfo().isBlank()) {
            validationResult.addError(new Err("info_incorrect", "Field with information is empty", "Поле с информацией не заполнено"));
        }

        return validationResult;
    }

}
