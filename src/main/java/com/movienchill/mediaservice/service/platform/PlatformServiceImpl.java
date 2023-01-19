package com.movienchill.mediaservice.service.platform;

import com.movienchill.mediaservice.domain.dto.PlatformDTO;
import com.movienchill.mediaservice.domain.model.Platform;
import com.movienchill.mediaservice.service.IGenericService;
import com.movienchill.mediaservice.utils.WebService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j
@Service
public class PlatformServiceImpl implements PlatformService, IGenericService<Platform, PlatformDTO> {
    private final String API_TMDB_URL = "https://api.themoviedb.org/3/";
    private final String API_TMDB_KEY = "729743283360120d3d45dc0dbb666a58";

    @Override
    public List<PlatformDTO> findAll() {
        return null;
    }

    @Override
    public PlatformDTO findById(Long id) {
        return null;
    }

    @Override
    public boolean create(PlatformDTO entityDto) {
        return false;
    }

    @Override
    public void save(Platform entity) {

    }

    @Override
    public void delete(Long id) {

    }

    private Integer researchId_TMDB(String movieName) {
        String response = WebService.get(API_TMDB_URL + "/search/movie?api_key=" + API_TMDB_KEY + "&query=" + movieName);
        JSONObject ob = new JSONObject(response);
        JSONArray results = ob.getJSONArray("results");
        if (results.length() != 0) {
            return ob.getJSONArray("results").getJSONObject(0).getInt("id");
        } else {
            return null;
        }

    }

    private int findInListByName(List<PlatformDTO> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public List<PlatformDTO> getPlatformInfo(String movieName) {
        Integer id_TMDB = researchId_TMDB(movieName);
        List<PlatformDTO> listPlateform = new ArrayList<>();
        if (id_TMDB == null) {
            return listPlateform;
        }

        String response = WebService.get(API_TMDB_URL + "/movie/" + id_TMDB + "/watch/providers?api_key=" + API_TMDB_KEY);
        JSONObject ob = new JSONObject(response);
        JSONObject results = ob.getJSONObject("results");
        Iterator<String> keys = results.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (results.get(key) instanceof JSONObject) {
                if (((JSONObject) results.get(key)).has("flatrate")) {
                    JSONArray flatrate = ((JSONObject) results.get(key)).getJSONArray("flatrate");
                    for (int i = 0; i < flatrate.length(); i++) {
                        String platform = flatrate.getJSONObject(i).getString("provider_name");
                        int index = findInListByName(listPlateform, platform);
                        if (index == -1) {
                            listPlateform.add(new PlatformDTO(platform, new ArrayList<String>() {{
                                add(key);
                            }}));
                        } else {
                            listPlateform.get(index).getCountry().add(key);
                        }
                    }
                }
            }

        }
        return listPlateform;
    }
}
