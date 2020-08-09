package com.isabelasoft.MessageAutomation.Database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.isabelasoft.MessageAutomation.Dao.ReceieveNumberDao;
import com.isabelasoft.MessageAutomation.Dao.ReceieveNumberDao_Impl;
import com.isabelasoft.MessageAutomation.Dao.StateDao;
import com.isabelasoft.MessageAutomation.Dao.StateDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MsgAutomationDB_Impl extends MsgAutomationDB {
  private volatile StateDao _stateDao;

  private volatile ReceieveNumberDao _receieveNumberDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `StateModel` (`ID` INTEGER PRIMARY KEY AUTOINCREMENT, `State` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ReceiverNumberModel` (`ID` INTEGER PRIMARY KEY AUTOINCREMENT, `IncomingNumber` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ab9f1d9a24419bf0d1d9c1f98795cd40')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `StateModel`");
        _db.execSQL("DROP TABLE IF EXISTS `ReceiverNumberModel`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsStateModel = new HashMap<String, TableInfo.Column>(2);
        _columnsStateModel.put("ID", new TableInfo.Column("ID", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStateModel.put("State", new TableInfo.Column("State", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStateModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStateModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStateModel = new TableInfo("StateModel", _columnsStateModel, _foreignKeysStateModel, _indicesStateModel);
        final TableInfo _existingStateModel = TableInfo.read(_db, "StateModel");
        if (! _infoStateModel.equals(_existingStateModel)) {
          return new RoomOpenHelper.ValidationResult(false, "StateModel(com.isabelasoft.MessageAutomation.Model.StateModel).\n"
                  + " Expected:\n" + _infoStateModel + "\n"
                  + " Found:\n" + _existingStateModel);
        }
        final HashMap<String, TableInfo.Column> _columnsReceiverNumberModel = new HashMap<String, TableInfo.Column>(2);
        _columnsReceiverNumberModel.put("ID", new TableInfo.Column("ID", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReceiverNumberModel.put("IncomingNumber", new TableInfo.Column("IncomingNumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReceiverNumberModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReceiverNumberModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReceiverNumberModel = new TableInfo("ReceiverNumberModel", _columnsReceiverNumberModel, _foreignKeysReceiverNumberModel, _indicesReceiverNumberModel);
        final TableInfo _existingReceiverNumberModel = TableInfo.read(_db, "ReceiverNumberModel");
        if (! _infoReceiverNumberModel.equals(_existingReceiverNumberModel)) {
          return new RoomOpenHelper.ValidationResult(false, "ReceiverNumberModel(com.isabelasoft.MessageAutomation.Model.ReceiverNumberModel).\n"
                  + " Expected:\n" + _infoReceiverNumberModel + "\n"
                  + " Found:\n" + _existingReceiverNumberModel);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "ab9f1d9a24419bf0d1d9c1f98795cd40", "4cb28031ccecbdef602cf787f8b740e2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "StateModel","ReceiverNumberModel");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `StateModel`");
      _db.execSQL("DELETE FROM `ReceiverNumberModel`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public StateDao stateDao() {
    if (_stateDao != null) {
      return _stateDao;
    } else {
      synchronized(this) {
        if(_stateDao == null) {
          _stateDao = new StateDao_Impl(this);
        }
        return _stateDao;
      }
    }
  }

  @Override
  public ReceieveNumberDao receiverNumberDao() {
    if (_receieveNumberDao != null) {
      return _receieveNumberDao;
    } else {
      synchronized(this) {
        if(_receieveNumberDao == null) {
          _receieveNumberDao = new ReceieveNumberDao_Impl(this);
        }
        return _receieveNumberDao;
      }
    }
  }
}
