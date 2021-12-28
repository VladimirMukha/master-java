package app.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_addresses")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String country;
    @Column
    private String city;
    @Column
    private int index;
    @Column
    private String street;
    @Column
    private int building;
    @Column
    private int room;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Transient
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "  country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", index=" + index +
                ", street='" + street + '\'' +
                ", building=" + building +
                ", room=" + room +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public Address() {
    }

    public static Address.Builder newBuilder() {
        return new Address().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder id(Integer id) {
            Address.this.id = id;
            return this;
        }

        public Builder country(String country) {
            Address.this.country = country;
            return this;
        }
        public Builder city(String city) {
            Address.this.city = city;
            return this;
        }
        public Builder index(Integer index) {
            Address.this.index = index;
            return this;
        }
        public Builder street(String street) {
            Address.this.street = street;
            return this;
        }
        public Builder building(Integer building) {
            Address.this.building = building;
            return this;
        }
        public Builder room(Integer room) {
            Address.this.room = room;
            return this;
        }
        public Builder user(User user) {
            Address.this.user = user;
            return this;
        }
        public Builder userId(Integer user) {
            Address.this.userId = user;
            return this;
        }

        public Address build() {
            return Address.this;
        }
    }
}