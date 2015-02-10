package jp.soraseed.fw.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.soraseed.fw.exception.SoraSeedFWRuntimelException;
import jp.soraseed.fw.utility.DBUtilities;

/**
 * DAO基底クラス
 * 
 */
public abstract class BaseDao {

    /** connection */
    private Connection con = null;

    /** PreparedStatement */
    private PreparedStatement pstmt = null;

    /** ResultSet */
    private ResultSet rs = null;

    /** SQL付加 */
    protected String otherSQL;

    
    /**
     * コンストラクタ
     * 
     * @param con
     *            connection
     */
    public BaseDao(Connection con) {
        this.con = con;
    }
    
    /**
     * SELECT用パラメータ設定
     * @param args　パラメータ
     * @param kbn　条件切り分け用区分
     */
    protected void setSQLForSelect(Object[] args, int kbn) {
    };
    
    /**
     * UPDATE用パラメータ設定
     * @param entity 更新エンティティ
     * @param args パラメータ
     * @param kbn 条件切り分け用区分
     */
    protected <T> void setSQLForUpdate(T entity, Object[] args, int kbn) {
    };
    
    /**
     * INSERT用パラメータ設定
     * @param entity 更新エンティティ
     * @param args パラメータ
     * @param kbn 条件切り分け用区分
     */
    protected <T> void setSQLForInsert(T args, int kbn) {
    };
    
    /**
     * DELETE用パラメータ設定
     * @param args パラメータ
     * @param kbn 条件切り分け用区分
     */
    protected <T> void setSQLForDelete(Object[] args, int kbn) {
    };
    
    
    
    
    /**
     * 検索
     * @param entity Bean
     * @param params パラメータ
     * @return List<entity>
     */
    public <T> List<T> select(T entity, Object... params) {
        return select(0, entity, params);
    }
    
    /**
     * 検索条件が複数あるとき
     * @param kbn 検索条件切り分け区分
     * @param entity Bean
     * @param params パラメータ
     * @return List<entity>
     */
    public <T> List<T> select(int kbn, T entity, Object... params) {

        List<T> list = new ArrayList<T>();
        try {

            setSQLForSelect(params, kbn);
            pstmt.setFetchSize(10000);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Object obj = entity.getClass().newInstance();
                int size = pstmt.getMetaData().getColumnCount();
                for (int i = 1; i <= size; i++) {

                    try {
                        /*
                         * DBから値を取得し、Entityに格納する。 例） USER_NAME →
                         * EntityのuserNameに値が格納される。
                         */
                        String name = pstmt.getMetaData().getColumnName(i);
                        String type = pstmt.getMetaData().getColumnTypeName(i);
                        Field field = obj.getClass().getDeclaredField(changeName(name));
                        field.setAccessible(true);
                        try {
                            field.set(obj, getFirld(name, type, rs));
                        } catch (IllegalArgumentException ie) {
                            try {
                                if ("NUMBER".equalsIgnoreCase(type)) {
                                    field.set(obj, rs.getDouble(name));
                                }
                            } catch (IllegalArgumentException iae) {
	                            try {
	                                if ("NUMBER".equalsIgnoreCase(type)) {
	                                    field.set(obj, rs.getLong(name));
	                                }
	                            } catch (IllegalArgumentException iaen) {
	                                if ("NUMBER".equalsIgnoreCase(type)) {
	                                    field.set(obj, rs.getInt(name));
	                                }
	                            }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                list.add((T) obj);
            }
        } catch (Exception e) {
            throw new SoraSeedFWRuntimelException(e);
        } finally {
            // ステートメントクローズ
            DBUtilities.closeRsAndPstmt(rs, pstmt);
        }

        return list;
    }

    /**
     * 更新
     *
     * @param entity 使用Entity
     * @return 更新件数
     */
    public <T> int update(T entity) {
        return update(0, entity);
    }
    
    /**
     * 
     * @param kbn
     * @param entity
     * @param params
     * @return
     */
    public <T> int update(int kbn, T entity, Object... params) {
        int updateCount = 0;

        try {

            setSQLForUpdate(entity, params, kbn);
            updateCount = pstmt.executeUpdate();


        } catch (Exception e) {
            try {
                con.rollback();
                if ((con != null) && (!con.isClosed())) {
                    con.close();
                }
            } catch (Exception ex) {

            }
            throw new SoraSeedFWRuntimelException(e);
        } finally {
            // ステートメントクローズ
            DBUtilities.closeRsAndPstmt(rs, pstmt);
        }
        return updateCount;
    }
    
    /**
     * 登録
     * @param entity 使用Entity
     * @return 更新件数
     */
    public <T> int insert(T param) {


        int insertCount = 0;
        try {

            setSQLForInsert(param, 0);
            insertCount = pstmt.executeUpdate();
        } catch (Exception e) {
            try {
                con.rollback();
                if ((con != null) && (!con.isClosed())) {
                    con.close();
                }
            } catch (Exception ex) {

            }
            throw new SoraSeedFWRuntimelException(e);

        } finally {
            // ステートメントクローズ
            DBUtilities.closeRsAndPstmt(rs, pstmt);
        }

        return insertCount;

    }
    
    /**
     * 複数件登録
     * @param param 登録用BeanList
     * @return　登録件数
     */
    public <T> int insertLoop(List<T> param) {
        return insertLoop(param ,0);
    }

    /**
     * 複数件登録
     * @param param パラメータ
     * @param kbn　条件切り分け用区分
     * @return　登録件数
     */
    public <T> int insertLoop(List<T> param, int kbn) {


        int insertCount = 0;
        try {
            setPstmt(null);
            for(int i = 0; param.size() > i; i++) {
                setSQLForInsert((T)param.get(i), kbn);
                pstmt.executeUpdate();
                insertCount++;
            }
            

        } catch (Exception e) {
            try {
                con.rollback();
                if ((con != null) && (!con.isClosed())) {
                    con.close();
                }
            } catch (Exception ex) {
            
            }
            
            throw new SoraSeedFWRuntimelException(e);      
        } finally {
            // ステートメントクローズ
            DBUtilities.closeRsAndPstmt(rs, pstmt);
        }

        return insertCount;

    }
    

    
    /**
     * 削除
     */
    public <T> int delete(Object... params) {

        return delete(0, params);
    }
    /**
     * 削除
     */
    public <T> int delete(int kbn, Object... params) {

        int deleteCount = 0;
        try {

            setSQLForDelete(params, kbn);
            deleteCount = pstmt.executeUpdate();

        } catch (Exception e) {
            try {
                con.rollback();
                if ((con != null) && (!con.isClosed())) {
                    con.close();
                }
            } catch (Exception ex) {}
            throw new SoraSeedFWRuntimelException(e); 
        } finally {
            // ステートメントクローズ
            DBUtilities.closeRsAndPstmt(rs, pstmt);
        }

        return deleteCount;

    }

    
    /**
     * フィールド名称変更
     * 
     * @param name フィールド名称
     * @return 変更後フィールド名称
     * 
     * <pre>
     *        すべて小文字にして、_（アンダーバー）を削除し、アンダーバーの後の文字を大文字にする
     * </pre>
     */
    private String changeName(String name) {

        String smallStr = name.toLowerCase();
        String[] devideStr = smallStr.split("_");
        StringBuilder sb = new StringBuilder(devideStr[0]);
        for (int i = 1; i < devideStr.length; i++) {

            if (devideStr[i].length() == 0) {
                continue;
            } else if (devideStr[i].length() == 1) {
                sb.append(devideStr[i].substring(0, 1).toUpperCase());
            } else if (devideStr[i].length() > 1) {
                sb.append(devideStr[i].substring(0, 1).toUpperCase()).append(
                        devideStr[i].substring(1));
            }
        }
        return sb.toString();
    }


    /**
     * フィールド取得
     * 
     * @param name フィールド名称
     * @param type フィールドの型
     * @param rs リザルトセット
     * @return フィールドの値
     * @throws Exception 例外
     */
    private Object getFirld(String name, String type, ResultSet rs) throws Exception {

        Object ret = null;
        if (("text".equals(type)) || ("bpchar".equals(type)) || ("varchar".equals(type))) {
            ret = rs.getString(name);
        } else if (("int4".equals(type)) || ("numeric".equals(type))) {
            ret = rs.getBigDecimal(name);
        } else if ("date".equals(type)) {
            ret = convertToUtilDate(rs.getDate(name));
        } else if ("timestamptz".equals(type)) {
            ret = convertToUtilTimestamp(rs.getTimestamp(name));
        } else {
            throw new SoraSeedFWRuntimelException();
        }
        return ret;
    }
    
    /**
     * @return con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con
     */
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * @return pstmt
     */
    public PreparedStatement getPstmt() {
        return pstmt;
    }
   
    /**
     * @param pstmt
     */
    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    /**
     * @return rs
     */
    public ResultSet getRs() {
        return rs;
    }

    /**
     * @param rs
     */
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    
    /**
     * その他ＳＱＬ
     * @return
     */
	public String getOtherSQL() {
        return otherSQL;
    }
    public void setOtherSQL(String otherSQL) {
        this.otherSQL = otherSQL;
    }

	/**
	 * Stringを日付に変換して返却
	 * 
	 * @param str
	 * @return
	 */
	protected java.sql.Date parseDate(String str) {

        java.sql.Date date = null;
            
        if ((str != null) && (!"".equals(str.trim()))) {
	
            try {
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    sdf.setLenient(false);
                    sdf.applyPattern("yyyy/MM/dd");
                    Date dat = sdf.parse(str.trim());
                    
                    SimpleDateFormat sdfForSql = new SimpleDateFormat();
                    sdfForSql.setLenient(false);
                    sdfForSql.applyPattern("yyyy-MM-dd");
                    date = java.sql.Date.valueOf(sdfForSql.format(dat));
	
			} catch (Exception e) {
			    throw new SoraSeedFWRuntimelException(e);
			}
		}
        return date;
	}

    protected java.sql.Date convertToSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
        
    }
    
    protected Date convertToUtilDate(java.sql.Date date) {
        return new Date(date.getTime());
        
    }
    
    protected java.sql.Timestamp convertToSqlTimestamp(Date date) {
        return new java.sql.Timestamp(date.getTime());
        
    }
    
    protected Date convertToUtilTimestamp(java.sql.Timestamp date) {
        return new Date(date.getTime());
        
    }

}