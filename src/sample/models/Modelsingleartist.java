package sample.models;

import sample.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Collin on 5-7-2017.
 */
public class Modelsingleartist {

        private static sample.models.Modelsingleartist ourInstance = new sample.models.Modelsingleartist();

        public static sample.models.Modelsingleartist getOurInstance(){
            return ourInstance;
        }

        public class singleartistItem{
            protected String singleartist;

            public singleartistItem(String artiestnaam){

                this.singleartist = artiestnaam;
            }

            public String getSingleartist() {
                return singleartist;
            }

            @Override
            public String toString(){
                return singleartist;
            }
        }

        class SingleartistIterator implements Iterator<sample.models.Modelsingleartist.singleartistItem> {
            protected ResultSet rs;
            protected Statement stmt;

            protected  void readNext(){
                try{
                    if ((rs !=null) && (rs.next())){
                        return;
                    }
                } catch (SQLException e){

                }

                if (rs !=null){
                    try{
                        rs.close();
                    } catch (SQLException sqlex){

                    }
                    rs = null;
                }

                if (stmt != null){
                    try{
                        stmt.close();
                    } catch (SQLException sqlex){

                    }
                    stmt = null;
                }
            }

            public SingleartistIterator(int test) {
                Connection connection = Database.getConnection();
                try {
                    stmt = connection.createStatement();
                    rs = stmt.executeQuery("SELECT titel FROM single WHERE artiest = "+test+" ORDER BY titel");
                } catch (SQLException ex){
                    System.out.println("sqlexception: "+ ex.getMessage());
                    System.out.println("sqlstate: "+ ex.getSQLState());
                    System.out.println("vendorerror: "+ ex.getErrorCode());
                    throw new Error("fout bui het uitvoeren");
                } finally {
                    readNext();
                }
            }
            @Override
            public boolean hasNext(){
                return rs!=null;
            }

            @Override
            public sample.models.Modelsingleartist.singleartistItem next() {
                if (rs==null){
                    throw new NoSuchElementException();
                }
                sample.models.Modelsingleartist.singleartistItem naam = null;
                try{
                    naam = new sample.models.Modelsingleartist.singleartistItem(rs.getString(1));
                } catch (SQLException e){
                    e.printStackTrace();
                    throw new Error("mislukt ophalen");
                }
                readNext();
                return naam;
            }
        }
        public Iterable<sample.models.Modelsingleartist.singleartistItem> getSingleartist(int test) {
            return new Iterable<singleartistItem>() {
                @Override
                public Iterator<sample.models.Modelsingleartist.singleartistItem> iterator() {
                    return new sample.models.Modelsingleartist.SingleartistIterator(test);
                }
            };
        }
    }
