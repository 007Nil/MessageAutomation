package com.isabelasoft.MessageAutomation.Dao;

import android.database.Cursor;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ReceieveNumberDao_Impl implements ReceieveNumberDao {
  private final RoomDatabase __db;

  private final SharedSQLiteStatement __preparedStmtOfInsertPhoneNo;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePhoneNo;

  public ReceieveNumberDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__preparedStmtOfInsertPhoneNo = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "INSERT INTO ReceiverNumberModel (incomingNumber) VALUES (?)";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePhoneNo = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE ReceiverNumberModel SET IncomingNumber=? WHERE id=1";
        return _query;
      }
    };
  }

  @Override
  public void insertPhoneNo(final String incomingNumber) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfInsertPhoneNo.acquire();
    int _argIndex = 1;
    if (incomingNumber == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, incomingNumber);
    }
    __db.beginTransaction();
    try {
      _stmt.executeInsert();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfInsertPhoneNo.release(_stmt);
    }
  }

  @Override
  public void updatePhoneNo(final String incomingNumber) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePhoneNo.acquire();
    int _argIndex = 1;
    if (incomingNumber == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, incomingNumber);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdatePhoneNo.release(_stmt);
    }
  }

  @Override
  public String findIncomingNumber() {
    final String _sql = "SELECT IncomingNumber FROM ReceiverNumberModel WHERE ID=1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
