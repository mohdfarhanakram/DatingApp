/**
 * 
 */
package com.farru.android.database;

import java.io.IOException;

import android.content.Context;
import android.util.Log;

/**
 * @author m.farhan
 *
 */
public class DbOperation {

	private Context mContext;
	private DatabaseHelper dbHelper;

	public DbOperation(Context ctx) {
		Log.i("inside", "GetSetDatabase()");
		mContext = ctx;
		dbHelper = new DatabaseHelper(ctx);

		try {
			dbHelper.createDataBase();
			Log.i("DATABASE CRETAED", "CRETAED");
		} catch (IOException i) {
			throw new Error("Unable to create Database");
		}
	}

/*	public ArrayList<Item> getItemList(){
		ArrayList<Item> savedItemList = new ArrayList<Item>();

		//String query = "SELECT id,list_name,COUNT(*) FROM Item_List_Table  GROUP BY id";

		String query = "SELECT * FROM Item_List_Master";

		try{

			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();

			Log.e("Sql query",query);
			Cursor cursor = database.rawQuery(query, null);

			if (cursor!=null && cursor.getCount() != 0) {
				Log.e("Sql query cursor size",cursor.getCount()+"");

				if (cursor.moveToFirst()) {
					do {
						Item item = new Item();
						item.setId(cursor.getString(0));
						item.setName(cursor.getString(1));
						item.setCount(cursor.getInt(2));
						savedItemList.add(item);

					} while (cursor.moveToNext());
				}
				cursor.close();
			}

			for(int i=0; i<savedItemList.size(); i++){
				Item item = savedItemList.get(i);
				item.setSubItemList(getItemsBasedOnId(item.getId(),database));
			}

			dbHelper.close();

		}catch(Exception e){
			e.printStackTrace();
		}

		return savedItemList;	
	}


	public ArrayList<Item> getItemsBasedOnId(String id){
		ArrayList<Item> subItemList = new ArrayList<Item>();
		try{

			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();

			String subQuery = "SELECT * FROM Item_List_Table  WHERE id="+id;

			Log.e("Sql sub query",subQuery);
			Cursor cursor = database.rawQuery(subQuery, null);

			if (cursor!=null && cursor.getCount() != 0) {
				Log.e("Sql sub query cursor size",cursor.getCount()+"");

				if (cursor.moveToFirst()) {
					do {
						Item item = new Item();
						item.setUid(cursor.getString(0));
						item.setTitle(cursor.getString(1));
						item.setSubTitle(cursor.getString(2));
						item.setQuantity(cursor.getString(3));
						item.setReminder(cursor.getInt(4));
						item.setExpiry(cursor.getString(5));
						item.setImageInByte(cursor.getBlob(6));
						item.setId(cursor.getString(7));
						subItemList.add(item);

					} while (cursor.moveToNext());
				}
				cursor.close();
			}

			dbHelper.close();

		}catch(Exception e){
			e.printStackTrace();
		}


		return subItemList;
	}


	public boolean saveListItemDataInDb(Item item){

		try{


			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();

			ContentValues contentValue = new ContentValues();   
			contentValue.put("id",  item.getId());
			contentValue.put("list_name", item.getName()); 
			contentValue.put("item_title", item.getTitle());
			contentValue.put("item", item.getSubTitle());
			contentValue.put("quantity", item.getQuantity());
			contentValue.put("reminder", item.getReminder());
			contentValue.put("expiry_date", item.getExpiry());
			contentValue.put("image", item.getImageInByte());

			database.insert("Item_List_Table", null, contentValue);

			dbHelper.close();


		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public boolean insertItemListTable(Item item){

		try{


			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();

			ContentValues contentValue = new ContentValues();  
			contentValue.put("uid",  System.currentTimeMillis());
			contentValue.put("item_title", item.getTitle());
			contentValue.put("item", item.getSubTitle());
			contentValue.put("quantity", item.getQuantity());
			contentValue.put("reminder", item.getReminder());
			contentValue.put("expiry_date", item.getExpiry());
			contentValue.put("image", item.getImageInByte());
			contentValue.put("id",  item.getId());

			database.insert("Item_List_Table", null, contentValue);

			dbHelper.close();


		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public String insertItemMasterTable(String id){

		try{


			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();

			ContentValues contentValue = new ContentValues();   
			contentValue.put("id",  id);
			contentValue.put("list_name", id); 
			contentValue.put("list_count", 0);

			database.insert("Item_List_Master", null, contentValue);
			
			ContentValues contentValue1 = new ContentValues();   
			contentValue1.put("id",  id);
			
			database.insert("Item_List_Table", null, contentValue1);

			dbHelper.close();


		}catch(Exception e){
			e.printStackTrace();
			return "";
		}

		return id;
	}


	public String createListTable(Item item){
		String uid = "";
		try{


			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();

			uid = System.currentTimeMillis()+"";

			ContentValues contentValue = new ContentValues();  
			contentValue.put("uid",  uid);
			contentValue.put("item_title", item.getTitle());
			contentValue.put("item", item.getSubTitle());
			contentValue.put("quantity", item.getQuantity());
			contentValue.put("reminder", item.getReminder());
			contentValue.put("expiry_date", item.getExpiry());
			contentValue.put("image", item.getImageInByte());
			contentValue.put("id",  item.getId());

			database.insert("Item_List_Table", null, contentValue);


			dbHelper.close();

			dbHelper.openDataBase();
			database = dbHelper.getWritableDatabase();

			int count = 0;
			try{
				String query = "SELECT * FROM Item_List_Table WHERE id="+item.getId();

				Log.e("Sql count query",query);
				Cursor cursor = database.rawQuery(query, null);

				if (cursor!=null && cursor.getCount() != 0) {
					count = cursor.getCount();
					cursor.close();
				}
			}catch(Exception e){

			}


			Log.e("Count : ",count+"");

			ContentValues contentMasterValue = new ContentValues(); 
			if(count==1){
				contentMasterValue.put("id",  item.getId());
				contentMasterValue.put("list_name", item.getName()); 
				contentMasterValue.put("list_count", count);
				database.insert("Item_List_Master", null, contentMasterValue);
			}else{
				contentMasterValue.put("list_count", count);
				database.update("Item_List_Master", contentMasterValue, "id="+item.getId(), null);
			}




			dbHelper.close();


		}catch(Exception e){
			e.printStackTrace();
			return "";
		}

		return uid;
	}

	public boolean deleteSelectedListTable(String[] whrArgs){

		try{

			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();

			try{
				database.delete("Item_List_Master", "id = ?",whrArgs);
			}catch(Exception e){e.printStackTrace();}

			try{
				database.delete("Item_List_Table", "id = ?",whrArgs);
			}catch(Exception e){e.printStackTrace();}



			dbHelper.close();

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public boolean deleteSelectedSubListTable(ArrayList<Object> data){

		try{

			String[] whrArgs = (String[])data.get(0);
			int count = (Integer)data.get(1);

			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();

			try{
				database.delete("Item_List_Table", "id = ?",whrArgs);
				if(whrArgs.length>0){
					ContentValues contentMasterValue = new ContentValues();
					contentMasterValue.put("list_count", count-whrArgs.length);
					database.update("Item_List_Master", contentMasterValue, "id="+whrArgs[0], null);
				}

			}catch(Exception e){e.printStackTrace();}


			dbHelper.close();

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public boolean updateListTable(ArrayList<Object> data){

		try{

			Item item = (Item)data.get(0);
			String pId = (String)data.get(1);

			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();

			int count = 0;
			try{
				String query = "SELECT * FROM Item_List_Table  WHERE id="+item.getId();
				Log.e("Sql count query",query);
				Cursor cursor = database.rawQuery(query, null);
				if (cursor!=null && cursor.getCount() != 0) {
					Log.e("Sql query cursor size",cursor.getCount()+"");

					if (cursor.moveToFirst()) {
						do {
							String uid = cursor.getString(0);
							if(!StringUtils.isNullOrEmpty(uid)){
								count++;
							}

						} while (cursor.moveToNext());
					}
					cursor.close();
				}
				
 			}catch(Exception e){

			}

			Log.e("Count : ",count+"");

			ContentValues contentValue = new ContentValues();  
			contentValue.put("item_title", item.getTitle());
			contentValue.put("item", item.getSubTitle());
			contentValue.put("quantity", item.getQuantity());
			contentValue.put("reminder", item.getReminder());
			contentValue.put("expiry_date", item.getExpiry());
			contentValue.put("image", item.getImageInByte());
			

			if(count==0){
				contentValue.put("uid",  System.currentTimeMillis()+"");
				database.update("Item_List_Table", contentValue, "id="+item.getId(),null);
				//database.insert("Item_List_Table", null, contentValue);
			}else{
				contentValue.put("id",  item.getId());
				database.update("Item_List_Table", contentValue, "uid="+item.getUid(),null);
			}
			
			dbHelper.close();
			dbHelper.openDataBase();
			database = dbHelper.getWritableDatabase();


			int c = 0;
			try{


				String query = "SELECT * FROM Item_List_Table WHERE id="+item.getId();

				Log.e("Sql count query",query);
				Cursor cursor = database.rawQuery(query, null);


				if (cursor!=null && cursor.getCount() != 0) {
					c = cursor.getCount();
					cursor.close();
				}


			}catch(Exception e){

			}

			Log.e("Count : ",c+"");
			ContentValues contentMasterValue = new ContentValues(); 
			contentMasterValue.put("list_count", c);
			database.update("Item_List_Master", contentMasterValue, "id="+item.getId(), null);	

			int c1 = 0;
			try{
				String query1 = "SELECT * FROM Item_List_Table WHERE id="+pId;

				Log.e("Sql count query",query1);
				Cursor cursor1 = database.rawQuery(query1, null);


				if (cursor1!=null && cursor1.getCount() != 0) {
					c1 = cursor1.getCount();
					cursor1.close();
				}
			}catch(Exception e){

			}

			Log.e("Count : ",c1+"");

			ContentValues contentMasterValue1 = new ContentValues(); 
			contentMasterValue1.put("list_count", c1);
			database.update("Item_List_Master", contentMasterValue1, "id="+pId, null);

			dbHelper.close();
		}catch(Exception e){
			return false;
		}
		return true;
	}

*/

}


