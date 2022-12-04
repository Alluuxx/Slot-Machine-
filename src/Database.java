import java.sql.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection dbConnection = null;
    private static Database dbInstance = null;
    private String path = null;
    public Boolean result = false;

    public static synchronized Database getInstance() {
        if (null == dbInstance) {
            dbInstance = new Database("MyDatabase");
        }
        return dbInstance;
    }

    private Database(String Path) {

        try {
            path = Path;
            open(path);
        } catch (SQLException e) {
            System.out.println("Log - SQLexception");
            e.printStackTrace();
        }

    }

    public void open(String dbName) throws SQLException {

        File databaseFile = new File(dbName);
        if (databaseFile.exists() && !databaseFile.isDirectory()) {
            System.out.println("Database file exists");
            result = true;
            String database = "jdbc:sqlite:" + dbName;
            dbConnection = DriverManager.getConnection(database);
        } else {
            result = false;
            initializeDatabase();
        }

    }

    //Initialisoi tietokannan ja samalla luo peli-id:n 1, joka toimii joka pelikerralla samana pelaajana
    private boolean initializeDatabase() throws SQLException {

        String dbName = "MyDatabase";
        path = dbName;

        String database = "jdbc:sqlite:" + dbName;
        dbConnection = DriverManager.getConnection(database);

        if (null != dbConnection) {
            String pelisessio = "create table pelisessio (peliId INTEGER(200) NOT NULL DEFAULT 1, fontti INTEGER(20) NOT NULL DEFAULT 1, saldo DOUBLE(8,2) NOT NULL DEFAULT 0.00, panoksensuuruus varchar(150), primary key(peliId))";
            String pelitiedot = "create table pelitiedot (peliId INTEGER(200) NOT NULL DEFAULT 1, kierrokset INTEGER(10000) NOT NULL DEFAULT 0, havitytKierrokset INTEGER(10000) NOT NULL DEFAULT 0, suurinvoittokerroin DOUBLE(4,2) NOT NULL DEFAULT 0.00, voittoTappioTilanne INTEGER(10000) NOT NULL DEFAULT 0, primary key(peliId), FOREIGN KEY(peliId) REFERENCES pelisessio(peliId))";
            Statement createStatement = dbConnection.createStatement();
            createStatement.executeUpdate(pelisessio);
            createStatement.executeUpdate(pelitiedot);
            createStatement.close();
            System.out.println("DB successfully created");

            String setId1 = null;
            String setId2 = null;
            setId1 = "insert into pelisessio (peliId)" + "VALUES('" + 1 + "')";
            setId2 = "insert into pelitiedot (peliId)" + "VALUES('" + 1 + "')";
            createStatement = dbConnection.createStatement();
            createStatement.executeUpdate(setId1);
            createStatement.executeUpdate(setId2);
            createStatement.close();

            result = true;
            return true;
        }

        System.out.println("DB creation failed");
        return false;

    }

    public void closeDB() throws SQLException {
        if (null != dbConnection) {
            dbConnection.close();
            System.out.println("closing db connection");
            dbConnection = null;
        }
    }

    public boolean setFont(int font) throws SQLException {
        String setFonttiString = null;
        setFonttiString = "update pelisessio set fontti = '" + font + "' where peliId = 1";
        Statement createStatement;
        createStatement = dbConnection.createStatement();
        createStatement.executeUpdate(setFonttiString);
        createStatement.close();

        return true;
    }

    public int getFont() throws SQLException {

        int fontti;
        String getFonttiString = null;
        getFonttiString = "select fontti from pelisessio where peliId = 1";
        Statement queryStatement;
        queryStatement = dbConnection.createStatement();
        fontti = queryStatement.executeQuery(getFonttiString).getInt("fontti");
        queryStatement.close();

        return fontti;

    }

    public boolean setSaldo(double saldo) throws SQLException {

        String setSaldoString = null;
        setSaldoString = "update pelisessio set saldo = '" + saldo + "' where peliId = 1";
        Statement createStatement;
        createStatement = dbConnection.createStatement();
        createStatement.executeUpdate(setSaldoString);
        createStatement.close();

        return true;

    }

    public boolean setKierrokset(int kierrokset) throws SQLException {

        String setKierroksetString = null;
        setKierroksetString = "update pelitiedot set kierrokset = '" + kierrokset + "' where peliId = 1";
        Statement createStatement;
        createStatement = dbConnection.createStatement();
        createStatement.executeUpdate(setKierroksetString);
        createStatement.close();

        return true;

    }

    public boolean setPanos(String panoksensuuruus) throws SQLException {

        String setPanosString = null;
        setPanosString = "update pelisessio set panoksensuuruus = '" + panoksensuuruus + "' where peliId = 1";
        Statement createStatement;
        createStatement = dbConnection.createStatement();
        createStatement.executeUpdate(setPanosString);
        createStatement.close();

        return true;

    }

    public boolean setHavityt(int havityt) throws SQLException {

        String setHavitytString = null;
        setHavitytString = "update pelitiedot set havitytKierrokset = '" + havityt + "' where peliId = 1";
        Statement createStatement;
        createStatement = dbConnection.createStatement();
        createStatement.executeUpdate(setHavitytString);
        createStatement.close();

        return true;

    }

    public boolean setSuurinKerroin(double suurinvoittokerroin) throws SQLException {

        String setSuurinKerroinString = null;
        setSuurinKerroinString = "update pelitiedot set suurinvoittokerroin = '" + suurinvoittokerroin
                + "' where peliId = 1";
        Statement createStatement;
        createStatement = dbConnection.createStatement();
        createStatement.executeUpdate(setSuurinKerroinString);
        createStatement.close();

        return true;

    }

    public boolean setVoittoTappio(int voittoTappio) throws SQLException {

        String setVoittoTappioString = null;
        setVoittoTappioString = "update pelitiedot set voittoTappioTilanne = '" + voittoTappio + "' where peliId = 1";
        Statement createStatement;
        createStatement = dbConnection.createStatement();
        createStatement.executeUpdate(setVoittoTappioString);
        createStatement.close();

        return true;

    }

    public double getSaldo() throws SQLException {

        double saldo;
        String getSaldoString = null;
        getSaldoString = "select saldo from pelisessio where peliId = 1";
        Statement queryStatement;
        queryStatement = dbConnection.createStatement();
        saldo = queryStatement.executeQuery(getSaldoString).getDouble("saldo");
        queryStatement.close();

        return saldo;

    }

    public String getPanos() throws SQLException {

        String saldo;
        String getSaldoString = null;
        getSaldoString = "select panoksensuuruus from pelisessio where peliId = 1";
        Statement queryStatement;
        queryStatement = dbConnection.createStatement();
        saldo = queryStatement.executeQuery(getSaldoString).getString("panoksensuuruus");
        queryStatement.close();

        return saldo;

    }

    public int getKierrokset() throws SQLException {

        int maara;
        String getKierroksetString = null;
        getKierroksetString = "select kierrokset from pelitiedot where peliId = 1";
        Statement queryStatement;
        queryStatement = dbConnection.createStatement();
        maara = queryStatement.executeQuery(getKierroksetString).getInt("kierrokset");
        queryStatement.close();

        return maara;

    }

    public int getHavitytKierrokset() throws SQLException {

        int havitytKierrokset;
        String getHavitytString = null;
        getHavitytString = "select havitytKierrokset from pelitiedot where peliId = 1";
        Statement queryStatement;
        queryStatement = dbConnection.createStatement();
        havitytKierrokset = queryStatement.executeQuery(getHavitytString).getInt("havitytKierrokset");
        queryStatement.close();

        return havitytKierrokset;

    }

    public double getSuurinVoittoKerroin() throws SQLException {

        double voittokerroin;
        String getSuurinVoittoKerroinString = null;
        getSuurinVoittoKerroinString = "select suurinvoittokerroin from pelitiedot where peliId = 1";
        Statement queryStatement;
        queryStatement = dbConnection.createStatement();
        voittokerroin = queryStatement.executeQuery(getSuurinVoittoKerroinString).getDouble("suurinvoittokerroin");
        queryStatement.close();

        return voittokerroin;

    }

    public int getVoittoTappioTilanne() throws SQLException {

        int voittotappio;
        String getVoittoTappioTilanneString = null;
        getVoittoTappioTilanneString = "select voittoTappioTilanne from pelitiedot where peliId = 1";
        Statement queryStatement;
        queryStatement = dbConnection.createStatement();
        voittotappio = queryStatement.executeQuery(getVoittoTappioTilanneString).getInt("voittoTappioTilanne");
        queryStatement.close();

        return voittotappio;

    }
}
