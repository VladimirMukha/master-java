package app.model;

import org.springframework.util.StringUtils;

public class ProductFilter {
    private Integer id;
    private String name;
    private Integer price;
    private Integer size;
    private String brand;
    private String color;
    private Integer category;
    private Integer quantityInStock;

    public ProductFilter() {
    }

    public ProductFilter(Integer id, String name, Integer price, Integer size,
                         String brand, String color, Integer category, Integer quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.brand = brand;
        this.color = color;
        this.category = category;
        this.quantityInStock = quantityInStock;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtils.isEmpty(name) ? null : name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = StringUtils.isEmpty(brand) ? null : color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = StringUtils.isEmpty(color) ? null : color;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public boolean isEmpty() {
        return id == null && name == null && price == null && size == null
                && brand == null && color == null && category == null && quantityInStock == null;
    }

    /**
     * Строит по заполненным атрибутам параметры запроса
     * @return Параметры запроса
     */
    public String getQuery() {
        StringBuilder sb = new StringBuilder();
        //Строим параметры запроса
        if (this.id != null) {
            sb.append("id").append(" =:").append("id");
        }
        if (this.name != null) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append("name").append(" =:").append("name");
        }
        if (this.price != null) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append("price").append(" =:").append("price");
        }
        if (this.size != null) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append("size").append(" =:").append("size");
        }

        if (this.brand != null) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append("brand").append(" =:").append("brand");
        }
        if (this.color != null) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append("color").append(" =:").append("color");
        }
        if (this.category != null) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append("category.id").append(" =:").append("category");
        }
        if (this.quantityInStock != null) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append("quantityInStock").append(" =:").append("quantityInStock");
        }
        return sb.toString();
    }
}
