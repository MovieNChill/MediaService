package com.movienchill.mediaservice.domain.repository.external.tmdb;

import com.movienchill.mediaservice.domain.dto.MediaDTO;
import com.movienchill.mediaservice.domain.dto.PlatformDTO;
import com.movienchill.mediaservice.utils.GlobalProperties;
import com.movienchill.mediaservice.utils.WebService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Repository
public class TmdbDAOImpl implements TmdbDAO {
    @Autowired
    private GlobalProperties globalProperties;

    @Override
    public Integer findIdByName(String name) {
        String response = WebService.get(globalProperties.getUrlTmdb() + "/search/movie?api_key=" + globalProperties.getApiKey() + "&query=" + name);
        JSONObject ob = new JSONObject(response);
        JSONArray results = ob.getJSONArray("results");
        if (results.length() != 0) {
            return ob.getJSONArray("results").getJSONObject(0).getInt("id");
        } else {
            return null;
        }
    }

    @Override
    public List<PlatformDTO> findPlatformInfoById(Integer id) {
        List<PlatformDTO> listPlateform = new ArrayList<>();
        String response = WebService.get(globalProperties.getUrlTmdb() + "/movie/" + id + "/watch/providers?api_key=" + globalProperties.getApiKey());
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

    @Override
    public MediaDTO findByName(String name) {
        return null;
    }

    private int findInListByName(List<PlatformDTO> list, String name){
        for (int i=0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
