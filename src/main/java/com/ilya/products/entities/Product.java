package com.ilya.products.entities;

import com.ilya.products.models.ProductDtoRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Product сущность в базе данных. С объектами этого класса
 * должно взаимодействовать приложение для различных преобразований.
 */
@Entity
@Table(schema = "app", name = "products")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    @Column(name = "vendor_code")
    private String vendorCode;
    private String description;
    private String category;
    private Double price;
    private Integer quantity;
    @Column(name = "create_at")
    private OffsetDateTime createAt;
    @Column(name = "quantity_last_updated")
    private OffsetDateTime quantityLastUpdated;

    /**
     * Дефолтный конструктор
     */
    public Product(ProductDtoRequest productDtoRequest) {

        this.id = productDtoRequest.getId();
        this.title = productDtoRequest.getTitle();
        this.vendorCode = productDtoRequest.getVendorCode();
        this.description = productDtoRequest.getDescription();
        this.category = productDtoRequest.getCategory();
        this.price = productDtoRequest.getPrice();
        this.quantity = productDtoRequest.getQuantity();
        this.createAt = OffsetDateTime.now();
        this.quantityLastUpdated = OffsetDateTime.of(
                createAt.toLocalDateTime(),
                createAt.getOffset()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id)
                && Objects.equals(title, product.title)
                && Objects.equals(vendorCode, product.vendorCode)
                && Objects.equals(description, product.description)
                && Objects.equals(category, product.category)
                && Objects.equals(price, product.price)
                && Objects.equals(quantity, product.quantity)
                && Objects.equals(createAt, product.createAt)
                && Objects.equals(quantityLastUpdated, product.quantityLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, vendorCode, description, category, price, quantity, createAt, quantityLastUpdated);
    }

    @Override
    public Product clone() {
        try {
            Product clone = (Product) super.clone();
            Product.builder()
                    .id(this.id)
                    .title(this.title)
                    .vendorCode(this.vendorCode)
                    .description(this.description)
                    .category(this.category)
                    .price(this.price)
                    .quantity(this.quantity)
                    .createAt(this.createAt != null ?
                            OffsetDateTime.of(this.createAt.toLocalDateTime(), this.createAt.getOffset())
                            : null)
                    .quantityLastUpdated(this.quantityLastUpdated != null ?
                            OffsetDateTime.of(this.quantityLastUpdated.toLocalDateTime(), this.quantityLastUpdated.getOffset())
                            : null)
                    .build();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}


