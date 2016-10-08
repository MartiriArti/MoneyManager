package com.whoami.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "categories")
public class CategoryEntity extends Model {

    @Column(name = "name")
    public String name;

    public CategoryEntity(){
        super();
    }

    public CategoryEntity(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<CategoryEntity> selectAll(){
        return new Select().from(CategoryEntity.class).execute();
    }

}