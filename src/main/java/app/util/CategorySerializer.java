package app.util;

import app.model.Category;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class CategorySerializer implements JsonSerializer<Category> {
    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        List<String> list = new LinkedList<>();
        list.add(String.valueOf(category.getId()));
        list.add(category.getName());
        return new JsonPrimitive(list.toString());
    }
}
