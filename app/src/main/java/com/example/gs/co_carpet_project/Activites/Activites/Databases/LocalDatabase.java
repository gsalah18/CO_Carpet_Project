	package com.example.gs.co_carpet_project.Activites.Activites.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;

import com.example.gs.co_carpet_project.Activites.Activites.Activites.Outcomes;
import com.example.gs.co_carpet_project.Activites.Activites.Activites.Sells;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Paids_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Bills_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Buys_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Check_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Customers_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Employee_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Outcomes_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Payment_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Sells_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Store_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.items_class;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;


public class LocalDatabase {
    Context context;
    SQLiteDatabase DB;
    public LocalDatabase(Context context) {
        this.context = context;
        DB=this.context.openOrCreateDatabase("carpet.db",Context.MODE_PRIVATE,null);
        Tables();
    }

    private void Tables(){
        DB.execSQL("CREATE TABLE IF NOT EXISTS goods (" +
                "name VARCHAR UNIQUE" +
                ");");
        DB.execSQL("CREATE TABLE IF NOT EXISTS country (" +
                "name VARCHAR UNUQUE" +
                ");");
        DB.execSQL("CREATE TABLE IF NOT EXISTS city (" +
                "name VARCHAR UNIQUE," +
                "country VARCHAR" +
                ");");

        DB.execSQL("CREATE TABLE IF NOT EXISTS customers (" +
                "name VARCHAR UNIQUE,"+
                "phone VARCHAR UNIQUE," +
                " address VARCHAR" +
                ");");
        DB.execSQL("CREATE TABLE IF NOT EXISTS outcomes (" +
                "name VARCHAR," +
                "price REAL," +
                "notes VARCHAR," +
                "date VARCHAR" +
                ");");
        //////////////////////////////////////////////////////////////
        DB.execSQL("CREATE TABLE IF NOT EXISTS buy (" +
                "carpet VARCHAR,"+
                "type VARCHAR,"+
                "size VARCHAR,"+
                "color VARCHAR,"+
                "amount INTEGER," +
                "price REAL," +
                "picture BLOB," +
                "address VARCHAR," +
                "city VARCHAR," +
                "country VARCHAR" +
                ");");
        DB.execSQL("CREATE TABLE IF NOT EXISTS store (" +
                "exported VARCHAR,"+
                "carpet VARCHAR,"+
                "type VARCHAR,"+
                "size VARCHAR,"+
                "color VARCHAR,"+
                "amount INTEGER," +
                "price REAL," +
                "picture BLOB" +
                ");");
        ///////////////////////////////////////////////////////////////
        DB.execSQL("CREATE TABLE IF NOT EXISTS employees (" +
                "name VARCHAR UNIQUE," +
                "phone VARCHAR UNIQUE," +
                "address VARCHAR," +
                "salary real," +
                "date VARCHAR" +
                ");");
        DB.execSQL("CREATE TABLE IF NOT EXISTS payments (" +
                "employee VARCHAR UNIQUE," +
                "money real," +
                "date VARCHAR" +
                ");");


        DB.execSQL("CREATE TABLE IF NOT EXISTS checks (" +
                "export VARCHAR," +
                "no VARCHAR UNIQUE," +
                "owner VARCHAR," +
                "price REAL," +
                "account VARCHAR," +
                "bank VARCHAR," +
                "date VARCHAR," +
                "picture BLOB" +
                ");");

        DB.execSQL("CREATE TABLE IF NOT EXISTS bills (" +
                "id INTEGER," +
                "customer VARCHAR," +
                "price REAL," +
                "paid REAL"+
                ");");

        DB.execSQL("CREATE TABLE IF NOT EXISTS paids (" +
                "id INTEGER," +
                "customer VARCHAR," +
                "price REAL," +
                "date VARCHAR" +
                ");");
        DB.execSQL("CREATE TABLE IF NOT EXISTS items(" +
                "id INTEGER," +
                "customer VARCHAR," +
                "item VARCHAR," +
                "amount INTEGER," +
                "price REAL" +
                ");");
        DB.execSQL("CREATE TABLE IF NOT EXISTS sells (" +
                "id INTEGER UNIQUE," +
                "date VARCHAR" +
                ");");
        DB.execSQL("CREATE TABLE IF NOT EXISTS sell_items (" +
                "id INTEGER," +
                "carpet VARCHAR," +
                "type VARCHAR," +
                "color VARCHAR," +
                "size VARCHAR," +
                "amount INTEGER," +
                "price REAL" +
                ");");
    }

    public void AddGood(String name){
        try {


            ContentValues values = new ContentValues();
            values.put("name", name);
            DB.insert("goods", null, values);
        }catch (Exception e){
            Message("هذا الصنف موجود");
        }
    }
	public void AddCountry(String name){
        try {
            ContentValues values = new ContentValues();
            values.put("name", name);
            DB.insert("country", null, values);
        }catch (Exception e){
            Message("هذه الدوله موجوده");
        }
    }
	public void AddCity(String name,String country){
        try {
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("country", country);
            DB.insert("city", null, values);
        }catch (Exception e){
            Message("هذه المدينه موجوده");
        }
    }
	public void AddCustomer(Customers_class customer){

            ContentValues values = new ContentValues();
            values.put("name", customer.getName());
            values.put("phone", customer.getPhone());
            values.put("address", customer.getAddress());
        try {
            DB.insert("customers", null, values);
        }catch (Exception e){
            Message("يوجد زبون بنفس الاسم");
        }
    }
	public void AddOutcome(Outcomes_class customer){
        try {

            ContentValues values = new ContentValues();
            values.put("name", customer.getOutcome());
            values.put("price", customer.getPrice());
            values.put("notes", customer.getNotes());
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            String date = df.format(today);
            Message(date);
            values.put("date",date);
            DB.insert("outcomes", null, values);
        }catch (SQLException e){
            Message("يوجد مصروف بنفس الاسم");
        }
    }
	public void AddBuy(Buys_class buy){
        ContentValues values=new ContentValues();
        values.put("carpet",buy.getCarpet());
        values.put("type",buy.getType());
        values.put("size",buy.getSize());
        values.put("color",buy.getColor());
        values.put("amount",buy.getAmount());
        values.put("price",buy.getPrice());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        buy.getPicture().compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        byte[] pic = outputStream.toByteArray();
        values.put("picture", pic);

        values.put("address", buy.getAddress());
        values.put("city", buy.getCity());
        values.put("country", buy.getCountry());
        DB.insert("buy",null,values);
    }
	public void AddStore(Store_class store) {
        ContentValues values=new ContentValues();
        values.put("exported",store.getExported());
        values.put("carpet",store.getCarpet());
        values.put("type",store.getType());
        values.put("size",store.getSize());
        values.put("color",store.getColor());
        values.put("amount",store.getAmount());
        values.put("price",store.getPrice());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        store.getPicture().compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        byte[] pic = outputStream.toByteArray();
        values.put("picture", pic);

        DB.insert("store",null,values);
    }
	public void AddEmployee(Employee_class employee){
        try {
            ContentValues values = new ContentValues();
            values.put("name", employee.getName());
            values.put("phone", employee.getPhone());
            values.put("address", employee.getAddress());
            values.put("salary", employee.getSalary());
            values.put("date", employee.getDate());
            DB.insert("employees", null, values);
        }catch (SQLException e){
            Message("وجد عامل بنفس الاسم");
        }
    }
    public LinkedList<Employee_class> getEmployee(){
        LinkedList<Employee_class>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("SELECT * FROM employees",null);
        while (c.moveToNext()){
            Employee_class d=new Employee_class(c.getString(0),c.getString(1),c.getString(2),Double.parseDouble(c.getString(3)),c.getString(4));
            Data.add(d);
        }
        return Data;
    }
	public void AddPayment(Payment_class payment){
        ContentValues values=new ContentValues();
        values.put("employee",payment.getEmployee());
        values.put("money",payment.getMoney());
        values.put("date",payment.getDate());
        DB.insert("payments",null,values);
    }
	public void AddCheck(Check_class check){
        try {
            ContentValues values = new ContentValues();
            values.put("export", check.getExport());
            values.put("no", check.getNo());
            values.put("owner", check.getOwner());
            values.put("price", check.getPrice());
            values.put("account", check.getAccount());
            values.put("bank", check.getBank());
            values.put("date", check.getDate());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            check.getPicture().compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            byte[] pic = outputStream.toByteArray();
            values.put("picture", pic);

            DB.insert("checks", null, values);
        }catch (SQLException e){
            Message("يوجد شك بنفس الرقم");
        }
    }
	
	public void AddPaid(int id,String customer,double price){
        try {
            String date = new SimpleDateFormat("y/MM/dd").format(new Date());
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("customer", customer);
            values.put("price", price);
            values.put("date", date);
            DB.insert("paids", null, values);
            DB.execSQL("update bills set paid=paid + " + price + " where id=" + id + " and customer='" + customer + "';");
        }catch (SQLException e){
            Message("هده الدفعه موجوده");
        }
    }
	public void AddBill(Bills_class bill){

        if(!CheckBill(bill.getId(),bill.getCustomer())){
            ContentValues values = new ContentValues();
            values.put("id", bill.getId());
            values.put("customer", bill.getCustomer());
            values.put("price", bill.getPrice());
            values.put("paid",bill.getPaid());
            DB.insert("bills", null, values);
        }else{
            Message("هدف الفاتوره موجوده");
        }
    }
	public void AddItem(items_class item){
        ContentValues values=new ContentValues();
        values.put("id",item.getId());
        values.put("customer",item.getCustomer());
        values.put("item",item.getItem());
        values.put("amount",item.getAmount());
        values.put("price",item.getPrice());
        DB.insert("items",null,values);
    }
	public void AddSellID(Sells_class sell){

            ContentValues values = new ContentValues();
            values.put("id", sell.getId());
            values.put("date", sell.getDate());
            DB.insert("sells", null, values);

    }
    public void AddSell(Sells_class sell,int id){

            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("carpet", sell.getCarpet());
            values.put("type", sell.getType());
            values.put("color", sell.getColor());
            values.put("size", sell.getSize());
            values.put("amount", sell.getAmount());
            values.put("price", sell.getPrice());
            DB.insert("sell_items", null, values);

    }
	/////////////////////GET METHODS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public LinkedList<String>getGoods(){
        LinkedList<String>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from goods",null);
        while (c.moveToNext())
            Data.add(c.getString(0));
        return Data;
    }
    public void DeleteGood(String good){
        DB.delete("goods","name='"+good+"'",null);

    }//////////////////////////////

    
    public LinkedList<String>getCountry(){
        LinkedList<String>Data=new LinkedList<>();
        Data.add("محلي");
        Cursor c=DB.rawQuery("select * from country",null);
        while (c.moveToNext())
            Data.add(c.getString(0));
        return Data;
    }
    public void DeleteCountry(String country){
        DB.delete("country","name='"+country+"'",null);
        DB.delete("city","country='"+country+"'",null);

    }//////////////////////////////

    
    public LinkedList<String>getCities(String country){
        LinkedList<String>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from city where country='"+country+"'",null);
        while (c.moveToNext())
            Data.add(c.getString(0));
        return Data;
    }
    public LinkedList<String>getLocalCities(){
        LinkedList<String>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from city where country='"+"محلي"+"'",null);
        while (c.moveToNext())
            Data.add(c.getString(0));
        return Data;
    }
    public void DeleteCity(String city){
        DB.delete("city","name='"+city+"'",null);

    }

    
    public LinkedList<Customers_class> getCustomers(){
        LinkedList<Customers_class>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("SELECT * FROM customers",null);
        while (c.moveToNext()){
            Customers_class d=new Customers_class(c.getString(0),c.getString(1),c.getString(2));

            Data.add(d);
        }
        return Data;
    }
    public void DeleteCustomer(String customer){
        DB.delete("customers","name='"+customer+"'",null);
        DB.delete("bills","customer='"+customer+"'",null);
    }

    
    public LinkedList<Outcomes_class>FilterOutComes(String type,String year,String month) throws ParseException {
        LinkedList<Outcomes_class>Data=getOutComes();
        LinkedList<Outcomes_class>temp= (LinkedList<Outcomes_class>) Data.clone();

        if(year.equals("none") && month.equals("none") && type.equals("none"))
            return temp;

        if(!year.equals("none") && !month.equals("none") && !type.equals("none")){
            int y=Integer.parseInt(year);
            int m=Integer.parseInt(month);
            for(int i=0;i<Data.size();i++){
                SimpleDateFormat sdfIn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = sdfIn.parse(Data.get(i).getDate());
                if(!(type.equals(Data.get(i).getOutcome()) && (date.getYear()+1900)==y && (date.getMonth()+1)==m))
                    temp.remove(Data.get(i));
            }
        }
        else if(!year.equals("none") && !month.equals("none")){
            int y=Integer.parseInt(year);
            int m=Integer.parseInt(month);
            for(int i=0;i<Data.size();i++){
                SimpleDateFormat sdfIn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = sdfIn.parse(Data.get(i).getDate());
                if(!((date.getYear()+1900)==y && (date.getMonth()+1)==m))
                    temp.remove(Data.get(i));
            }
        }else if(!year.equals("none") && !type.equals("none")){
            int y=Integer.parseInt(year);
            for(int i=0;i<Data.size();i++){
                SimpleDateFormat sdfIn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = sdfIn.parse(Data.get(i).getDate());
                if(!((date.getYear()+1900)==y && type.equals(Data.get(i).getOutcome())))
                    temp.remove(Data.get(i));
            }
        }else if(!month.equals("none") && !type.equals("none")){
            int m=Integer.parseInt(month);
            for(int i=0;i<Data.size();i++){
                SimpleDateFormat sdfIn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = sdfIn.parse(Data.get(i).getDate());
                if(!(((date.getMonth()+1)==m) && type.equals(Data.get(i).getOutcome())))
                    temp.remove(Data.get(i));
            }
        }else if(!type.equals("none")){
            for(int i=0;i<Data.size();i++){
                if(!(type.equals(Data.get(i).getOutcome())))
                    temp.remove(Data.get(i));
            }
        }else if(!month.equals("none")){
            int m=Integer.parseInt(month);
            for(int i=0;i<Data.size();i++){

                SimpleDateFormat sdfIn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = sdfIn.parse(Data.get(i).getDate());
                    if (!(((date.getMonth() + 1) == m)))
                        temp.remove(Data.get(i));

            }
        }else if(!year.equals("none")){
            int y=Integer.parseInt(year);
            for(int i=0;i<Data.size();i++){
                SimpleDateFormat sdfIn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = sdfIn.parse(Data.get(i).getDate());
                if(!((date.getYear()+1900)==y))
                    temp.remove(Data.get(i));
            }

        }
        return temp;
    }

    public LinkedList<Outcomes_class> getOutComes(){
        LinkedList<Outcomes_class>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("SELECT * FROM outcomes",null);
        while (c.moveToNext()){
            Outcomes_class d=new Outcomes_class(c.getString(0),Double.parseDouble(c.getString(1)),c.getString(2),c.getString(3));
            Data.add(d);
        }
        c.close();
        return Data;
    }
    public void DeleteOutcome(Outcomes_class outcome){
        DB.delete("outcomes","name='"+outcome.getOutcome()+"' and date='"+outcome.getDate()+"' and price='"+outcome.getPrice()+"'",null);
    }

    
    public LinkedList<Buys_class>getBuy(int carpet){
        LinkedList<Buys_class>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from buy where carpet="+carpet,null);
        while (c.moveToNext()){
            Bitmap pic=BitmapFactory.decodeByteArray(c.getBlob(6),0,c.getBlob(6).length);
            Buys_class buy=new Buys_class(Integer.parseInt(c.getString(0)),c.getString(1),c.getString(2),c.getString(3),
                    Integer.parseInt(c.getString(4)),Double.parseDouble(c.getString(5)),pic,c.getString(7),c.getString(8),c.getString(9));
            Data.add(buy);
        }
        return Data;
    }
    public void DeleteBuy(Buys_class buy){
        DB.delete("buy","carpet="+buy.getCarpet()+" and type='"+buy.getType()+"' and size='"+buy.getSize()+"' and color='"+buy.getColor()+"' and " +
                "amount="+buy.getAmount()+" and price="+buy.getPrice()+" and address='"+buy.getAddress()+"' and city='"+buy.getCity()+"' " +
                " and counry='"+buy.getCountry()+"'",null);
    }

    
    public LinkedList<Store_class>getStore(int carpet){
        LinkedList<Store_class>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from store where carpet="+carpet,null);
        while (c.moveToNext()){
            Bitmap pic=BitmapFactory.decodeByteArray(c.getBlob(7),0,c.getBlob(7).length);
            Store_class buy=new Store_class(Integer.parseInt(c.getString(0)),Integer.parseInt(c.getString(1)),c.getString(2),c.getString(3)
                    ,c.getString(4),
                    Integer.parseInt(c.getString(5)),Double.parseDouble(c.getString(6)),pic);
            Data.add(buy);
        }
        return Data;
    }
    public void DeleteStore(Store_class buy){
        DB.delete("store","carpet="+buy.getCarpet()+" and type='"+buy.getType()+"' and size='"+buy.getSize()+"' and color='"+buy.getColor()+"' and " +
                "amount="+buy.getAmount()+" and price="+buy.getPrice(),null);
    }
    public void UpdateStore(Store_class buy){
        if(buy.getAmount()!=0) {
            ContentValues values = new ContentValues();
            values.put("amount", buy.getAmount());
            DB.update("store", values, "carpet=" + buy.getCarpet() + " and type='" + buy.getType() + "' and size='" + buy.getSize()
                    + "' and color='" + buy.getColor() + "' and " +
                      " price=" + buy.getPrice(), null);
        }else {
            try {
                DB.delete("store", "carpet=" + buy.getCarpet() + " and type='" + buy.getType() + "' and size='" + buy.getSize()
                        + "' and color='" + buy.getColor() + "'" +
                        " and price=" + buy.getPrice(), null);
            }catch (Exception e){
                Message(e+"");
            }
        }
    }

    
    public void DeleteEmployee(String name){
        DB.delete("employees","name='"+name+"'",null);
        DeletePayment(name);
    }

    
    public LinkedList<Payment_class>getPayments(){
        LinkedList<Payment_class>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from payments",null);
        while (c.moveToNext())
            Data.add(new Payment_class(c.getString(0),Double.parseDouble(c.getString(1)),c.getString(2)));

        return Data;
    }
    public void DeletePayment(String employee){
        DB.delete("payments","employee='"+employee+"'",null);
    }
    public LinkedList<String> getEmployeesNames(){
        LinkedList<String>names=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from employees",null);
        while (c.moveToNext())
            names.add(c.getString(0));

        return names;
    }



    
    public LinkedList<Check_class>getChecks(boolean export){
        LinkedList<Check_class>Data=new LinkedList<>();
        Cursor c= DB.rawQuery("select * from checks",null);
        while (c.moveToNext()){
            Bitmap pic= BitmapFactory.decodeByteArray(c.getBlob(7),0,c.getBlob(7).length);
            Data.add(new Check_class(c.getString(0),c.getString(1),c.getString(2),Double.parseDouble(c.getString(3)),c.getString(4),
                    c.getString(5),c.getString(6),pic));
        }
        LinkedList<Check_class>D=new LinkedList<>();
        D=(LinkedList)Data.clone();
        if(export) {
            for (int i = 0;i<D.size();i++) {
                if (D.get(i).getExport().length() == 0)
                    D.remove(i);
            }
        }else {
            for (int i = 0;i<D.size();i++){
                if (D.get(i).getExport().length() >0)
                    D.remove(i);
            }
        }

        return D;
    }
    public void DeleteCheck(String no){
        DB.delete("checks","no='"+no+"'",null);
    }

    
    public boolean CheckBill(int id,String customer){
        Cursor c=DB.rawQuery("select * from bills where customer='"+customer+"';",null);
        LinkedList<Integer>ids=new LinkedList<>();
        while (c.moveToNext())
            ids.add(Integer.parseInt(c.getString(0)));

        return ids.contains(id);
    }
    public LinkedList<Bills_class>getBills(String customer) {
        LinkedList<Bills_class> Data = new LinkedList<>();
        Cursor c = DB.rawQuery("select * from bills where customer='" + customer + "'", null);

        while (c.moveToNext()){
            Bills_class bill=new Bills_class(Integer.parseInt(c.getString(0)), c.getString(1), Double.parseDouble(c.getString(2))
                    ,Double.parseDouble(c.getString(3)));
            Data.add(bill);

        }
        return Data;
    }
    public void DeleteBill(int id,String customer){
        DB.delete("bills","id="+id+" and customer='"+customer+"'",null);
        DB.execSQL("delete from paids where id="+id+" and customer='"+customer+"';");
        DB.execSQL("delete from items where id="+id+" and customer='"+customer+"';");
    }
    public LinkedList<String>getCustomerName(){
        LinkedList<String>AllNames=new LinkedList<>();
        LinkedList<String>Names=new LinkedList<>();
        Cursor c=DB.rawQuery("select name from customers",null);
        while (c.moveToNext())
            Names.add(c.getString(0));
        return Names;
    }
    public LinkedList<String>getCustomerWithBill(){
        HashSet<String>HH=new HashSet<>();
        Cursor c=DB.rawQuery("select * from customers,bills where name=customer",null);
        while (c.moveToNext())
            HH.add(c.getString(0));

        LinkedList<String>Names=new LinkedList<>(HH);
        return Names;
    }
    public double getAllBillsSum(String customer){
        Cursor c=DB.rawQuery("select * from bills where customer='"+customer+"'",null);
        double sum=0;
        while (c.moveToNext())
            sum+=Double.parseDouble(c.getString(2));

        return sum;
    }
    
    public LinkedList<items_class>getItems(int id,String customer){
        LinkedList<items_class>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from items where id="+id+" and customer='"+customer+"'",null);
        while(c.moveToNext())
            Data.add(new items_class(id,customer,c.getString(2), Integer.parseInt(c.getString(3)),Double.parseDouble(c.getString(4))));
        c.close();
        return Data;
    }

    
    public LinkedList<Paids_class>getPaid(int id, String customer){
        LinkedList<Paids_class>Data=new LinkedList<>();
        Cursor c= DB.rawQuery("select * from paids where id="+id+" and customer='"+customer+"'",null);
        if(c.getCount()>0) {
            while (c.moveToNext())
                Data.add(new Paids_class(Double.parseDouble(c.getString(2)), c.getString(3)));
        }
        else Message("لا توجد اي دفعات");
        return Data;
    }

    

    public LinkedList<Sells_class>getSell(int id){
        LinkedList<Sells_class>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from sell_items where id="+id,null);
        while (c.moveToNext())
            Data.add(new Sells_class(id,c.getString(1),c.getString(2),c.getString(3),c.getString(4)
                    ,Integer.parseInt(c.getString(5)),Double.parseDouble(c.getString(6))));

        return Data;
    }
    public LinkedList<Sells_class>getSellIds(){
        LinkedList<Sells_class>Data=new LinkedList<>();
        Cursor c=DB.rawQuery("select * from sells",null);
        while (c.moveToNext())
            Data.add(new Sells_class(Integer.parseInt(c.getString(0)),c.getString(1)));

        return Data;
    }
    public void DeleteSell(int id){
        DB.delete("sells","id="+id,null);
        DB.delete("sell_items","id="+id,null);
    }

    public void Message(String msg){
        AlertDialog.Builder adb=new AlertDialog.Builder(this.context);
        adb.setMessage(msg);
        adb.setNegativeButton("حسنا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.show();
    }

}
