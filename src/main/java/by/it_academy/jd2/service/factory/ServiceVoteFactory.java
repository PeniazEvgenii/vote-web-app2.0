package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.mapper.factory.MapperVoteEntityFactory;
import by.it_academy.jd2.service.ServiceVote;
import by.it_academy.jd2.service.api.IServiceVote;
import by.it_academy.jd2.storage.factory.VoteStorageFactory;
import by.it_academy.jd2.validation.factory.FormVoteValidateFactory;

public class ServiceVoteFactory {
    private static final IServiceVote instance = new ServiceVote(
            VoteStorageFactory.getInstance(),
            FormVoteValidateFactory.getInstance(),
            MapperVoteEntityFactory.getInstance()
    );

    private ServiceVoteFactory() {
    }

    public static IServiceVote getInstance() {
        return instance;
    }
}
