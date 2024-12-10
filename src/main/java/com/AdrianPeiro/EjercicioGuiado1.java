package com.AdrianPeiro;

import java.sql.*;

public class EjercicioGuiado1 {
    public static void main(String[] args) {
        Connection conexion = null;
        Statement sentenciaSQL = null;
        ResultSet resultadoSQL = null;

        try{
            //Crear conexion.
            conexion = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite.db");

            System.out.println("Conexión establecida");
            System.out.println("**********");

            //Crear consulta.

            String sql = "SELECT * FROM Empleats";
            sentenciaSQL = conexion.createStatement();
            resultadoSQL= sentenciaSQL.executeQuery(sql);

            while (resultadoSQL.next()){
                String nif = resultadoSQL.getString("nif");
                String nombre = resultadoSQL.getString("nom");
                String apellidos = resultadoSQL.getString("cognoms");
                Double salario = resultadoSQL.getDouble("salari");

                System.out.println(nif + "\t" + nombre + "\t" + apellidos + "\t" + salario);


            }
            System.out.println("\n Mostra tots els empleats amb un salari superior a 2000 \n");

            String sql1 =  "SELECT * FROM Empleats WHERE Salari > 2000";
            sentenciaSQL = conexion.createStatement();
            resultadoSQL= sentenciaSQL.executeQuery(sql1);
            while (resultadoSQL.next()){
                String nif1 = resultadoSQL.getString("nif");
                String nombre1 = resultadoSQL.getString("nom");
                String apellidos1 = resultadoSQL.getString("cognoms");
                Double salario1 = resultadoSQL.getDouble("salari");
                System.out.println(nif1 + "\t" + nombre1 + "\t" + apellidos1 + "\t" + salario1);
            }



            resultadoSQL.close();
            sentenciaSQL.close();
            conexion.close();


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }










}
