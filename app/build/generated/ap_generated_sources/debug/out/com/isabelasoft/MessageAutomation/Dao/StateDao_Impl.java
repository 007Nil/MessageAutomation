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
public final class StateDao_Impl implements StateDao {
  private final RoomDatabase __db;

  private final SharedSQLiteStatement __preparedStmtOfInsetState;

  private final SharedSQLiteStatement __preparedStmtOfUpdateState;

  public StateDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__preparedStmtOfInsetState = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "INSERT INTO StateModel (State) VALUES (?)";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateState = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE StateModel SET State=? WHERE id=1";
        return _query;
      }
    };
  }

  @Override
  public void insetState(final String state) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfInsetState.acquire();
    int _argIndex = 1;
    if (state == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, state);
    }
    __db.beginTransaction();
    try {
      _stmt.executeInsert();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfInsetState.release(_stmt);
    }
  }

  @Override
  public void updateState(final String state) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateState.acquire();
    int _argIndex = 1;
    if (state == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, state);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateState.release(_stmt);
    }
  }

  @Override
  public String findState() {
    final String _sql = "SELECT State from StateModel WHERE id=1";
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
