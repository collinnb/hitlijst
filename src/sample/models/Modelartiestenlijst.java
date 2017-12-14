package sample.models;

import sample.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Collin on 2-7-2017.
 */
public class Modelartiestenlijst {
    private static Modelartiestenlijst ourInstance = new Modelartiestenlijst();

    public static Modelartiestenlijst getOurInstance(){
        return ourInstance;
    }

    public class artiestenlijstItem{
        protected String artiestnaam;
        protected int id;

        public artiestenlijstItem(String artiestnaam, int id){

            this.artiestnaam = artiestnaam;
            this.id = id;
        }

        public String getArtiestnaam() {
            return artiestnaam;
        }
        public int getid() {
            return id;
        }

        @Override
        public String toString(){
            return artiestnaam;
        }
    }

    class ArtiestenlijstIterator implements Iterator<artiestenlijstItem>{
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

        public ArtiestenlijstIterator(String test) {
            Connection connection = Database.getConnection();
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery("SELECT naam , id FROM artiest WHERE naam LIKE '%"+test+"%' ORDER BY naam");
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
        public  artiestenlijstItem next() {
            if (rs==null){
                throw new NoSuchElementException();
            }
            artiestenlijstItem naam = null;
            try{
                naam = new artiestenlijstItem(rs.getString(1), rs.getInt(2));
            } catch (SQLException e){
                e.printStackTrace();
                throw new Error("mislukt ophalen");
            }
            readNext();
            return naam;
        }
    }
    public Iterable<artiestenlijstItem> getArtiestenlijst(String test) {
        return new Iterable<artiestenlijstItem>() {
            @Override
            public Iterator<artiestenlijstItem> iterator() {
                return new ArtiestenlijstIterator(test);
            }
        };
    }
}
