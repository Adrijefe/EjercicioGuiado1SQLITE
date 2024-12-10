package com.AdrianPeiro;

import java.sql.*;

public class Ejercicio2 {
    public static void main(String[] args) {
        Statement statement = null;
        ResultSet resultSet = null;
        String url = "jdbc:sqlite:src/main/resources/empresa.db";
        try(Connection connection = DriverManager.getConnection(url)){

            System.out.println("Conexión establecida");



            String sql = "CREATE TABLE IF NOT EXISTS Departaments(" +
                    "IdDepartament INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NomDepartament VARCHAR(100) NOT NULL," +
                    "Responsable VARCHAR(100));";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Se ha creado");

            System.out.println("Senguda consulta");

            String sql1 = "ALTER TABLE Empleats ADD COLUMN IdDepartament INTEGER REFERENCES Departaments(IdDepartament);";
             statement.executeUpdate(sql1);
            System.out.println("Se ha modificado");

            System.out.println("Tercera consulta");

            String sql2 ="INSERT INTO Departaments (NomDepartament, Responsable) VALUES\n" +
                    "('Recursos Humans', 'Marta Pérez'),\n" +
                    "('Desenvolupament', 'Jaume Martí'),\n" +
                    "('Comptabilitat', 'Fina Soler');";
            statement.executeUpdate(sql2);

            System.out.println("Se ha insertado correctamente guapeton");

            System.out.println("Cuarta consulta");
            String sql3= "UPDATE Empleats SET IdDepartament = CASE\n" +
                    "    WHEN NIF = '123456789' THEN 1\n" +
                    "    WHEN NIF = '987654321' THEN 2\n" +
                    "    WHEN NIF = '111111111' THEN 3\n" +
                    "    WHEN NIF = '222222222' THEN 1\n" +
                    "    WHEN NIF = '333333333' THEN 2\n" +
                    "    WHEN NIF = '444444444' THEN 3\n" +
                    "END;";

            statement.executeUpdate(sql3);
            System.out.println("Se ha modificado");

            System.out.println("Consulta cinco");
            String sql4 = "SELECT e.NIF, e.Nom, e.Cognoms, d.NomDepartament\n" +
                    "FROM Empleats e\n" +
                    "LEFT JOIN Departaments d ON e.IdDepartament = d.IdDepartament;";

            resultSet = statement.executeQuery(sql4);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("Nom"));
                System.out.println(resultSet.getString("Cognoms"));
                System.out.println(resultSet.getString("NomDepartament"));

            }








        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
