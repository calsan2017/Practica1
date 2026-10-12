
package org.example.dao;

import org.example.modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private final String url =
            "jdbc:mariadb://localhost:3306/empresa";

    private final String usuario = "root";
    private final String password = "TU_PASSWORD";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                url, usuario, password
        );
    }

    // LISTAR CLIENTES
    public List<Cliente> listar() throws SQLException {
        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM clientes ORDER BY id";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email")
                );

                lista.add(cliente);
            }
        }

        return lista;
    }

    // BUSCAR CLIENTE POR ID
    public Cliente buscar(int id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email")
                    );
                }
            }
        }

        return null;
    }

    // INSERTAR CLIENTE
    public void insertar(Cliente cliente) throws SQLException {
        String sql =
                "INSERT INTO clientes (nombre, email) VALUES (?, ?)";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());

            ps.executeUpdate();
        }
    }

    // ACTUALIZAR CLIENTE
    public void actualizar(Cliente cliente) throws SQLException {
        String sql =
                "UPDATE clientes SET nombre = ?, email = ? WHERE id = ?";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.setInt(3, cliente.getId());

            ps.executeUpdate();
        }
    }

    // ELIMINAR CLIENTE
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
