package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.InfoFromUserDTO;
import by.it_academy.jd2.entity.VoteEntity;
import by.it_academy.jd2.mapper.api.IMapperDtoToEntity;
import by.it_academy.jd2.service.api.IServiceVote;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.validation.ValidFormException;
import by.it_academy.jd2.validation.ValidationResult;
import by.it_academy.jd2.validation.api.IValidateFormInfo;

public class ServiceVote implements IServiceVote {

    private final IValidateFormInfo formVoteUserValidate;
    private final IMapperDtoToEntity mapperVoteEntity;
    private final IStorage<VoteEntity> voteStorage;

    public ServiceVote(IStorage<VoteEntity> voteStorage,
                       IValidateFormInfo formVoteUserValidate,
                       IMapperDtoToEntity mapperVoteEntity
    ){
        this.voteStorage = voteStorage;
        this.formVoteUserValidate = formVoteUserValidate;
        this.mapperVoteEntity = mapperVoteEntity;
    }

    /**
     * Метод сохранения информации о голосовании в storage.
     * Метод перед сохранением информации в storage осуществляет валидацию переданной информации.
     * При несоответствии данных требованиям, пробрасывается исключение ValidFormException
     * @param infoFromUserDto - объект дто с данными о голосовании
     */
    @Override
    public void create(InfoFromUserDTO infoFromUserDto) {
        ValidationResult validationResult = formVoteUserValidate.valid(infoFromUserDto);
        if(!validationResult.checkErrorEmpty()) {
            throw new ValidFormException(validationResult.getErrors());
        }

        VoteEntity voteEntity = mapperVoteEntity.mapFrom(infoFromUserDto);

        voteStorage.create(voteEntity);

    }

}
