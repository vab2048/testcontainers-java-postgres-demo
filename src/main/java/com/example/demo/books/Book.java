package com.example.demo.books;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;


@Table("book")
public final class Book {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("status")
    private InventoryStatus status;

     public Book(String name) {
         this.name = name;
         this.status = InventoryStatus.AVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public InventoryStatus getStatus() {
        return status;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Book) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    @Override
    public String toString() {
        return "Book[" +
                "bookId=" + id + ", " +
                "bookName=" + name + ", " +
                "status=" + status + ']';
    }

}
