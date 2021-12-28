package app.service;

import app.dao.ScoreboardDAO;
import app.model.Product;
import app.util.ProductSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreboardServiceImpl implements ScoreboardService {
    private ScoreboardDAO scoreboardDAO;

    @Autowired
    public void setScoreboardDAO(ScoreboardDAO scoreboardDAO) {
        this.scoreboardDAO = scoreboardDAO;
    }

    @Transactional
    @Override
    public List<String> getListToScoreboard() {
        List<Product> products = scoreboardDAO.getMapToScoreboard();
        List<String> result = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(Product.class, new ProductSerializer())
                .create();
        for (Product product : products) {
            result.add(gson.toJson(product));
        }
        return result;
    }
}
