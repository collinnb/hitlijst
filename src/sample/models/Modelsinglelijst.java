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
public class Modelsinglelijst {
    private static Modelsinglelijst ourInstance = new Modelsinglelijst();

    public static Modelsinglelijst getOurInstance(){
        return ourInstance;
    }

    public class singlelijstItem{
        protected String singlenaam;
        protected String artiestnaam;
        protected int id;

        public singlelijstItem(String singlenaam,String artiestnaam,int id){

            this.singlenaam = singlenaam;
            this.artiestnaam = artiestnaam;
            this.id = id;
        }

        public String getSinglenaam() {
            return singlenaam;
        }

        public String getArtiestnaam() {
            return artiestnaam;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString(){
            return singlenaam;
        }
    }

    class SinglelijstIterator implements Iterator<Modelsinglelijst.singlelijstItem> {
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

        public SinglelijstIterator(String test) {
            Connection connection = Database.getConnection();
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery("SELECT titel , artiest , naam, single.id FROM single, artiest WHERE artiest = artiest.id AND titel LIKE '%"+test+"%' ORDER BY titel");
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
        public Modelsinglelijst.singlelijstItem next() {
            if (rs==null){
                throw new NoSuchElementException();
            }
            Modelsinglelijst.singlelijstItem naam = null;
            try{
                naam = new Modelsinglelijst.singlelijstItem(rs.getString(1), rs.getString(3),rs.getInt(4));
            } catch (SQLException e){
                e.printStackTrace();
                throw new Error("mislukt ophalen");
            }
            readNext();
            return naam;
        }
    }
    public Iterable<Modelsinglelijst.singlelijstItem> getArtiestenlijst(String test) {
        return new Iterable<singlelijstItem>() {
            @Override
            public Iterator<Modelsinglelijst.singlelijstItem> iterator() {
                return new Modelsinglelijst.SinglelijstIterator(test);
            }
        };
    }
}
