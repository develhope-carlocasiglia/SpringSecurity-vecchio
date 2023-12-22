package co.develhope.LoginDemo.order.entities;

import co.develhope.LoginDemo.utils.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;
    private String address;
    private String number;
    private String city;
    private String zipCode;
    private String state;

    private double price;

}
