package com.movienchill.mediaservice.domain.specification.builder;

import com.movienchill.mediaservice.domain.model.Media;
import com.movienchill.mediaservice.domain.specification.SpecificationSearch;
import com.movienchill.mediaservice.domain.specification.search.SearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialArray;

@Slf4j
public class SpecificationBuilder {
    private final List<SearchCriteria> params;
    private final String PATTERN_SEARCH_REGEX = "(\\w+?)(:|<|>)(([0-9]+(?:[.][0-9]+))|[a-zA-Z0-9_ àèìòùÀÈÌÒÙáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãñõÃÑÕäëïöüÿÄËÏÖÜŸçÇßØøÅåÆæœ¨*()Ã©-]+?|\\w+?-\\w+?-\\w+?)\\,";

    public SpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final SpecificationBuilder with(final String key, final String operation, final Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Media> build() {
        if (params.size() == 0)
            return null;

        List<Specification<Media>> specifications = params.stream().map(SpecificationSearch::new)
                .collect(Collectors.toList());

        Specification<Media> result = specifications.get(0);
        for (int i = 0; i < params.size(); i++) {
            result = Specification.where(result).and(specifications.get(i));
        }

        return result;
    }

    public Specification<Media> searchFilter(String search) {
        try {
            // Search of filters
            Pattern pattern = Pattern.compile(PATTERN_SEARCH_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                // Case where values to search are Boolean
                if (matcher.group(3).equals("true")) {
                    with(matcher.group(1), matcher.group(2), Boolean.TRUE);

                    search = search.replaceFirst(matcher.group(1), "");
                    search = search.replaceFirst(matcher.group(2), "");
                    search = search.replaceFirst(",", "");
                } else if (matcher.group(3).equals("false")) {
                    with(matcher.group(1), matcher.group(2), Boolean.FALSE);

                    search = search.replaceFirst(matcher.group(1), "");
                    search = search.replaceFirst(matcher.group(2), "");
                    search = search.replaceFirst(",", "");
                } else {
                    // Case where values are String
                    with(matcher.group(1), matcher.group(2), matcher.group(3));

                    search = search.replaceFirst(matcher.group(1), "");
                    search = search.replaceFirst(matcher.group(2), "");
                    search = search.replaceFirst(matcher.group(3), "");
                    search = search.replaceFirst(",", "");
                }
            }
            if (!search.isEmpty()) {
                if (search.contains(",")) {
                    for (String s : search.split(",")) {
                        with("*", ":", s);
                    }
                } else {
                    with("*", ":", search);
                }
            }
        } catch (Exception e) {
            log.error("An error occured while analysing the filter : {}", e.getMessage());
        }
        return build();

    }
}
