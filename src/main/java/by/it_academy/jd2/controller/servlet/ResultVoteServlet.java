package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.dto.SortedDateDTO;
import by.it_academy.jd2.dto.TextTimeString;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.service.api.IServiceGetData;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.service.factory.ServiceArtistFactory;
import by.it_academy.jd2.service.factory.ServiceGenreFactory;
import by.it_academy.jd2.service.factory.ServiceGetDataFactory;
import by.it_academy.jd2.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static by.it_academy.jd2.util.PathUtil.RESULT_VOTE_SERVLET;

@WebServlet(urlPatterns = RESULT_VOTE_SERVLET)
public class ResultVoteServlet extends HttpServlet {
    public static final String ATTRIBUTE_GENRE_SORT = "janresort";
    public static final String ATTRIBUTE_SINGER_SORT = "singersort";
    public static final String ATTRIBUTE_TEXT_SORT = "textsort";
    public static final String ATTRIBUTE_MAP_SINGERS = "test1";
    public static final String ATTRIBUTE_MAP_GENRES = "test2";
    public static final String JSP_PAGE_WITH_RESULT = "resultVote";

    IServiceGetData serviceGetData = ServiceGetDataFactory.getInstance();
    IArtistService singerService = ServiceArtistFactory.getInstance();
    IGenreService genreService = ServiceGenreFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SortedDateDTO sortedDateDTO = serviceGetData.getData();
        List<TextTimeString> textAndTimeVotes = sortedDateDTO.getTextAndTimeVotes();
        List<Map.Entry<Long, Long>> sortSing = sortedDateDTO.getSortSing();
        List<Map.Entry<Long, Long>> sortGenre = sortedDateDTO.getSortJanr();

        req.setAttribute(ATTRIBUTE_MAP_SINGERS, singerService.getAll());
        req.setAttribute(ATTRIBUTE_MAP_GENRES, genreService.getAll());
        req.setAttribute(ATTRIBUTE_SINGER_SORT, sortSing);
        req.setAttribute(ATTRIBUTE_GENRE_SORT, sortGenre);
        req.setAttribute(ATTRIBUTE_TEXT_SORT, textAndTimeVotes);

        req.getRequestDispatcher(JspUtil.getPath(JSP_PAGE_WITH_RESULT)).forward(req, resp);

    }
}
