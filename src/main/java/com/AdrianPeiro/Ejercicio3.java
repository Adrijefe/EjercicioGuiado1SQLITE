package com.AdrianPeiro;

import java.sql.*;

import java.util.Scanner;

public class Ejercicio3 {
    public static void main(String[] args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String url = "jdbc:sqlite:src/main/resources/empresa.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                Scanner sc = new Scanner(System.in);

                System.out.println("Consulta 1: Consultar todos los empleados");
                String consulta1 = "SELECT Nif, Nom, Cognoms FROM Empleats";
                resultSet = stmt.executeQuery(consulta1);
                while (resultSet.next()) {
                    String nif = resultSet.getString("Nif");
                    String nom = resultSet.getString("Nom");
                    String cognoms = resultSet.getString("Cognoms");
                    System.out.println(nif + " " + nom + " " + cognoms);
                }
                resultSet.close();

                System.out.println("Consulta 2: Empleados en un departamento específico");
                System.out.println("Dime el nombre del departamento:");
                String nomDepartament = sc.nextLine();
                String consulta2 = "SELECT Empleats.Nif, Empleats.Nom, Empleats.Cognoms " +
                        "FROM Empleats " +
                        "JOIN Departaments ON Empleats.IdDepartament = Departaments.IdDepartament " +
                        "WHERE Departaments.NomDepartament = ?";
                preparedStatement = conn.prepareStatement(consulta2);
                preparedStatement.setString(1, nomDepartament);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String nif = resultSet.getString("Nif");
                    String nom = resultSet.getString("Nom");
                    String cognoms = resultSet.getString("Cognoms");
                    System.out.println(nif + " " + nom + " " + cognoms);
                }
                preparedStatement.close();
                resultSet.close();

                System.out.println("Consulta 3: Empleados con salario superior a un valor");
                System.out.println("Dime un valor mínimo de salario:");
                double salariMinim = sc.nextDouble();
                sc.nextLine(); // Consumir el salto de línea
                String consulta3 = "SELECT Nif, Nom, Cognoms FROM Empleats WHERE Salari > ?";
                preparedStatement = conn.prepareStatement(consulta3);
                preparedStatement.setDouble(1, salariMinim);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String nif = resultSet.getString("Nif");
                    String nom = resultSet.getString("Nom");
                    String cognoms = resultSet.getString("Cognoms");
                    System.out.println(nif + " " + nom + " " + cognoms);
                }
                preparedStatement.close();
                resultSet.close();

                System.out.println("Consulta 4: Empleados en departamentos con más de 2 empleados");
                String consulta4 = "SELECT Nif, Nom, Cognoms FROM Empleats WHERE IdDepartament IN " +
                        "(SELECT IdDepartament FROM Empleats GROUP BY IdDepartament HAVING COUNT(*) > 2)";
                resultSet = stmt.executeQuery(consulta4);
                while (resultSet.next()) {
                    String nif = resultSet.getString("Nif");
                    String nom = resultSet.getString("Nom");
                    String cognoms = resultSet.getString("Cognoms");
                    System.out.println(nif + " " + nom + " " + cognoms);
                }
                resultSet.close();

                System.out.println("Consulta 5: Información completa de un empleado específico");
                System.out.println("Dime el NIF de un empleado:");
                String nifConsulta = sc.nextLine();
                String consulta5 = "SELECT e.*, d.NomDepartament FROM Empleats e " +
                        "JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                        "WHERE e.Nif = ?";
                preparedStatement = conn.prepareStatement(consulta5);
                preparedStatement.setString(1, nifConsulta);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String nom = resultSet.getString("Nom");
                    String cognoms = resultSet.getString("Cognoms");
                    String nomDept = resultSet.getString("NomDepartament");
                    double salari = resultSet.getDouble("Salari");
                    System.out.println("Empleado: " + nifConsulta + ", Nombre: " + nom + ", Apellidos: " + cognoms +
                            ", Departamento: " + nomDept + ", Salario: " + salari);
                } else {
                    System.out.println("Empleado no encontrado.");
                }
                preparedStatement.close();
                resultSet.close();

                System.out.println(" Consulta 6: Empleados con salario superior al salario medio del departamento");
                String consulta6 = "SELECT e.Nif, e.Nom, e.Cognoms FROM Empleats e " +
                        "WHERE e.Salari > (SELECT AVG(Salari) FROM Empleats WHERE IdDepartament = e.IdDepartament)";
                resultSet = stmt.executeQuery(consulta6);
                while (resultSet.next()) {
                    String nif = resultSet.getString("Nif");
                    String nom = resultSet.getString("Nom");
                    String cognoms = resultSet.getString("Cognoms");
                    System.out.println(nif + " " + nom + " " + cognoms);
                }
                resultSet.close();

                System.out.println(" Consulta 7: Departamentos con suma total de salarios superior a un valor");
                System.out.println("Dime un valor para la suma total de salarios:");
                double sumaTotal = sc.nextDouble();
                sc.nextLine();

                String consulta7 = "SELECT d.NomDepartament FROM Departaments d " +
                        "WHERE (SELECT SUM(Salari) FROM Empleats WHERE IdDepartament = d.IdDepartament) > ?";
                preparedStatement = conn.prepareStatement(consulta7);
                preparedStatement.setDouble(1, sumaTotal);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String nomDept = resultSet.getString("NomDepartament");
                    System.out.println(nomDept);
                }
                preparedStatement.close();
                resultSet.close();

                System.out.println("Consulta 8: Empleados sin departamento asignado");
                String consulta8 = "SELECT Nif, Nom, Cognoms FROM Empleats WHERE IdDepartament IS NULL";
                resultSet = stmt.executeQuery(consulta8);
                while (resultSet.next()) {
                    String nif = resultSet.getString("Nif");
                    String nom = resultSet.getString("Nom");
                    String cognoms = resultSet.getString("Cognoms");
                    System.out.println(nif + " " + nom + " " + cognoms);
                }
                resultSet.close();

                System.out.println(" Consulta 9: Salario máximo y mínimo por departamento");
                System.out.println("Dime el nombre del departamento:");
                String nomDeptConsulta = sc.nextLine();
                String consulta9 = "SELECT MAX(Salari) AS SalarioMax, MIN(Salari) AS SalarioMin FROM Empleats " +
                        "WHERE IdDepartament = (SELECT IdDepartament FROM Departaments WHERE NomDepartament = ?)";
                preparedStatement = conn.prepareStatement(consulta9);
                preparedStatement.setString(1, nomDeptConsulta);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    double salarioMax = resultSet.getDouble("SalarioMax");
                    double salarioMin = resultSet.getDouble("SalarioMin");
                    System.out.println("Salario máximo: " + salarioMax + ", Salario mínimo: " + salarioMin);
                } else {
                    System.out.println("Departamento no encontrado o sin empleados.");
                }
                preparedStatement.close();
                resultSet.close();

                System.out.println("Consulta 10: Empleados en departamentos con la palabra 'Desenvolupament'");

                String consulta10 = "SELECT e.Nif, e.Nom, e.Cognoms FROM Empleats e " +
                        "JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                        "WHERE d.NomDepartament LIKE ?";
                preparedStatement = conn.prepareStatement(consulta10);
                preparedStatement.setString(1, "%Desenvolupament%");
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String nif = resultSet.getString("Nif");
                    String nom = resultSet.getString("Nom");
                    String cognoms = resultSet.getString("Cognoms");
                    System.out.println(nif + " " + nom + " " + cognoms);
                }
                preparedStatement.close();
                resultSet.close();

                sc.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}