package com.movienchill.mediaservice;

//package com.movienchill.mediaservice.api;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.assertj.core.api.Assertions.assertThat;
import com.movienchill.mediaservice.controller.MediaRestController;
import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.repository.MediaDAO;
import com.movienchill.mediaservice.domain.specification.builder.SpecificationBuilder;
import com.movienchill.mediaservice.service.IGenericService;
import com.movienchill.mediaservice.service.media.MediaService;
import com.movienchill.mediaservice.service.media.MediaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(SpringExtension.class)
public class MediaServiceTest {

    @InjectMocks
    private MediaServiceImpl mediaService;

    @Mock
    private MediaDAO mediaDAO;

    /**
     * Test testGetMediasById(id)
     *
     * @throws Exception
     */

    @Test
    public void testGetMediasById() throws Exception {

        List<String> writterList = new ArrayList<>();
        writterList.add("Charles");

        List<String> starList = new ArrayList<>();
        starList.add("Charles");
        starList.add("Nicolas");
        starList.add("Timothée");
        starList.add("Laurine");
        starList.add("Yoann");

        Date date = new Date();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String stringDate = "2023-01-10T08:56:43.847+00:00";
        try {
            date = df1.parse(stringDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        MediaDTO mediaTest = new MediaDTO(1L, "Movie", "Test - The Movie", null, date, "Charles B", writterList,
                starList, "C'est l'histoire d'un test pour MovieNChill", null);
        // GIVEN
        Media media = new Media();
        media.setDescription("description");
        // media.setGenre("genre");
        when(mediaDAO.findById(1L)).thenReturn(Optional.of(media));
        // WHEN
        MediaDTO mediaId1 = mediaService.findById(1L);
        // THEN
        assertThat(media)
                .usingRecursiveComparison()
                .ignoringFields("genre").isEqualTo(mediaId1);
    }


    /**
     * Test testFindAllWithFilter(Specification, PageRequest)
     *
     * @throws Exception
     */
    @Test
    public void testFindAllWithFilter() throws Exception {

        // Filter analysis and Specification build
        SpecificationBuilder specificationBuilder = new SpecificationBuilder();
        Specification<String> spec = specificationBuilder.searchFilter("name: Test");

        List<MediaDTO> mediasList = new ArrayList<>();
        mediasList = mediaService.findAllWithFilter(spec, PageRequest.of(0, 15));
        assertThat(mediasList).isEmpty();
    }
}