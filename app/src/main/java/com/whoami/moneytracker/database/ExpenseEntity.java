package com.whoami.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "expenses")
public class ExpenseEntity extends Model {

    @Column(name = "price")
    public String price;
    @Column(name = "name")
    public String name;
    @Column(name = "date")
    public String date;
    @Column(name = "category", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public CategoryEntity category;
    @Column(name = "sum")
    public String sum;

    public ExpenseEntity(){
        super();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public static List<ExpenseEntity> selectAll(String query){
        return new Select().from(ExpenseEntity.class)
                .where("name LIKE?", new String[] {'%' + query + '%'})
                .execute();
    }

    public String getSum() {
        return sum;
    }

    public static ExpenseEntity selectById(int query){
        return new Select().from(ExpenseEntity.class)
                .where("id LIKE?", query)
                .executeSingle();
    }

    public static void deleteItem(String id){
        new Delete().from(ExpenseEntity.class)
                .where("name = ?", new String[]{"%"+ id +"%"})
                .execute();
    }

}
