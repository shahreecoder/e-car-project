package com.example.e_carservices.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class addtocart extends SQLiteOpenHelper {
    public addtocart(Context context) {
        super(context, "cart.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tblcart ( id Integer Primary Key AUTOINCREMENT,customer_id Text, product_id Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tblcart");
    }
    public Boolean insertAddtocart(String customerid, String productid){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValue=new ContentValues();

        contentValue.put("customer_id",customerid);
        contentValue.put("product_id",productid);

        long result=db.insert("tblcart",null,contentValue);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor getcartdata(String customer_id){


        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor=db.rawQuery("select * from tblcart where customer_id=?", new String []{customer_id});
        return cursor;

    }

    public Boolean clearCart(String customer_id){
        SQLiteDatabase db= this.getWritableDatabase();
        long result=db.delete("tblcart","customer_id=?",new String[]{customer_id});
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean removeitem(String customerid, String product_id){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("tblcart","product_id=? and customer_id=?",new String[]{product_id,customerid});
        return true;
    }
    public Boolean checkalready(String customer_id, String product_id){
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor=db.rawQuery("select count(*) from tblcart where product_id='"+product_id+"' and customer_id='"+customer_id+"'",null);

        int check=0;
        String temp="";
        if(cursor.moveToFirst()){
            do{

               temp= cursor.getString(0);





            }while (cursor.moveToNext());
        }
        cursor.close();

        if(Integer.parseInt(temp)>0){
            return false;
        }else{
            return true;
        }


    }


}
