package com.example.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MiBaseDatos extends SQLiteOpenHelper {
	
	
	private static final int VERSION_BASEDATOS = 1;
	private static final String NOMBRE_BASEDATOS = "mibasedatos.db";
    private static final String TABLA_CONTACTOS = "CREATE TABLE contactos (_id INT PRIMARY KEY, nombre TEXT, telefono INT, email TEXT)";
    
	
	
    
	public MiBaseDatos(Context context) {
		super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLA_CONTACTOS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLA_CONTACTOS);
        onCreate(db);
	}
	
	
	
	
	public void insertarCONTACTO(int id, String nom, int tlf, String email) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			ContentValues valores = new ContentValues();
			valores.put("_id", id);
			valores.put("nombre", nom);
			valores.put("telefono", tlf);
			valores.put("email", email);
			db.insert("contactos", null, valores);
		}
		db.close();   
	}
	
	
	public void modificarCONTACTO(int id, String nom, int tlf, String email){
	    SQLiteDatabase db = getWritableDatabase();
	    ContentValues valores = new ContentValues();
	    valores.put("_id", id);
	    valores.put("nombre", nom);
	    valores.put("telefono", tlf);
	    valores.put("email", email);
	    db.update("contactos", valores, "_id=" + id, null);
	    db.close();   
	}

	public void borrarCONTACTO(int id) {
	    SQLiteDatabase db = getWritableDatabase();
	    db.delete("contactos", "_id=" + id, null);
	    db.close();  
	}
	
	public Contactos recuperarCONTACTO(int id) {
	    SQLiteDatabase db = getReadableDatabase();
	    String[] valores_recuperar = {"_id", "nombre", "telefono", "email"};
	    Cursor c = db.query("contactos", valores_recuperar, "_id=" + id, null, null, null, null, null);
	    if(c != null) {
	        c.moveToFirst();
	    }
	    Contactos contactos = new Contactos(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3));
        db.close();
        c.close();
        return contactos;
	}
	
	
	public List<Contactos> recuperarCONTACTOS() {
	    SQLiteDatabase db = getReadableDatabase();
	    List<Contactos> lista_contactos = new ArrayList<Contactos>();
	    String[] valores_recuperar = {"_id", "nombre", "telefono", "email"};
	    Cursor c = db.query("contactos", valores_recuperar, null, null, null, null, null, null);
	    c.moveToFirst();
	    do {
	    	Contactos contactos = new Contactos(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3));
	    	lista_contactos.add(contactos);
		} while (c.moveToNext());
        db.close();
        c.close();
        return lista_contactos;
	}
}
