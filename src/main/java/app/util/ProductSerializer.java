package app.util;

import app.model.Product;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ProductSerializer implements JsonSerializer<Product> {
    @Override
    public JsonElement serialize(Product product, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("name", product.getName());
        result.addProperty("price", product.getPrice());
        return result;
    }
}
