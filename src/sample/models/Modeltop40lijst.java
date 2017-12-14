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
public class Modeltop40lijst {
    private static Modeltop40lijst ourInstance = new Modeltop40lijst();

    public static Modeltop40lijst getOurInstance(){
        return ourInstance;
    }

    public class top40Item{
        protected int positie;
        protected String singlenaam;
        protected String artiestnaam;
        protected int singleid;
        protected int artiestid;

        public top40Item(int positie,String singlenaam,String artiestnaam,int singleid,int artiestid){
            this.positie = positie;
            this.singlenaam = singlenaam;
            this.artiestnaam = artiestnaam;
            this.singleid = singleid;
            this.artiestid = artiestid;
        }

        public int getPositie() {
            return positie;
        }

        public String getSinglenaam() {
            return singlenaam;
        }

        public String getArtiestnaam() {
            return artiestnaam;
        }

        public int getSingleid() {
            return singleid;
        }

        public int getArtiestid() {
            return artiestid;
        }

        @Override
        public String toString(){
            return singlenaam;
        }
    }

    class top40Iterator implements Iterator<Modeltop40lijst.top40Item> {
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

        public top40Iterator(int test, int test2, int test3) {
            Connection connection = Database.getConnection();
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery("SELECT positie, titel , naam, single.id, artiest.id FROM single, artiest, hitlijst_notering, hitlijst_editie WHERE single.artiest = artiest.id AND hitlijst_notering.single = single.id " +
                        "AND hitlijst_editie.id = hitlijst_notering.hitlijst_editie  AND hitlijst_editie.jaar ="+test+" AND hitlijst_editie.week ="+test2+" AND hitlijst_editie.hitlijst = "+test3+" ORDER BY positie");

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
        public Modeltop40lijst.top40Item next() {
            if (rs==null){
                throw new NoSuchElementException();
            }
            Modeltop40lijst.top40Item naam = null;
            try{
                naam = new Modeltop40lijst.top40Item(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getInt(4),rs.getInt(5));
            } catch (SQLException e){
                e.printStackTrace();
                throw new Error("mislukt ophalen");
            }
            readNext();
            return naam;
        }
    }
    public Iterable<Modeltop40lijst.top40Item> getArtiestenlijst(int test,int test2,int test3) {
        return new Iterable<top40Item>() {
            @Override
            public Iterator<Modeltop40lijst.top40Item> iterator() {
                return new Modeltop40lijst.top40Iterator(test, test2,test3);
            }
        };
    }
}
