package by.it_academy.jd2.validation.factory;

import by.it_academy.jd2.service.factory.ServiceArtistFactory;
import by.it_academy.jd2.service.factory.ServiceGenreFactory;
import by.it_academy.jd2.validation.FormVoteValidate;
import by.it_academy.jd2.validation.api.IValidateFormInfo;

public class FormVoteValidateFactory {
    private static final IValidateFormInfo instance = new FormVoteValidate(
            ServiceArtistFactory.getInstance(), ServiceGenreFactory.getInstance()
    );

    private FormVoteValidateFactory() {}

    public static IValidateFormInfo getInstance() {
        return instance;
    }
}
