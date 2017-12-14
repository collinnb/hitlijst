package sample.models;

import sample.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Collin on 6-7-2017.
 */
public class Modelsingleinfo {
    private static Modelsingleinfo ourInstance = new Modelsingleinfo();

    public static Modelsingleinfo getOurInstance(){
        return ourInstance;
    }

    public class singleinfoItem{
        protected String singlenaam;
        protected String artiestnaam;
        protected int weeken;
        protected int hoogsteb;

        public singleinfoItem(String singlenaam,String artiestnaam,int weeken,int hoogsteb){
            this.singlenaam = singlenaam;
            this.artiestnaam = artiestnaam;
            this.weeken = weeken;
            this.hoogsteb = hoogsteb;
        }


        public String getSinglenaam() {
            return singlenaam;
        }

        public String getArtiestnaam() {
            return artiestnaam;
        }

        public int getWeeken() {
            return weeken;
        }

        public int getHoogsteb() {
            return hoogsteb;
        }

        @Override
        public String toString(){
            return singlenaam;
        }
    }

    class singleinfoIterator implements Iterator<Modelsingleinfo.singleinfoItem> {
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

        public singleinfoIterator(int test) {
            Connection connection = Database.getConnection();
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery("SELECT count(hitlijst_notering.single) AS aantalweek, titel, naam, MIN(hitlijst_notering.positie) AS hoogstenotering FROM hitlijst_notering, single, artiest WHERE hitlijst_notering.single = "+test+" AND single.id = "+test+" AND single.artiest = artiest.id ORDER BY positie");

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
        public Modelsingleinfo.singleinfoItem next() {
            if (rs==null){
                throw new NoSuchElementException();
            }
            Modelsingleinfo.singleinfoItem naam = null;
            try{
                naam = new Modelsingleinfo.singleinfoItem(rs.getString(2),rs.getString(3), rs.getInt(1),rs.getInt(4));
            } catch (SQLException e){
                e.printStackTrace();
                throw new Error("mislukt ophalen");
            }
            readNext();
            return naam;
        }
    }
    public Iterable<Modelsingleinfo.singleinfoItem> getsingleinfo(int test) {
        return new Iterable<singleinfoItem>() {
            @Override
            public Iterator<Modelsingleinfo.singleinfoItem> iterator() {
                return new Modelsingleinfo.singleinfoIterator(test);
            }
        };
    }
}
