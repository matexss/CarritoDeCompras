    package ec.edu.ups.dao.impl;

    import ec.edu.ups.dao.UsuarioDAO;
    import ec.edu.ups.modelo.Pregunta;
    import ec.edu.ups.modelo.RespuestaSeguridad;
    import ec.edu.ups.modelo.Rol;
    import ec.edu.ups.modelo.Usuario;

    import java.io.*;
    import java.util.*;

    public class UsuarioDAOArchivoTexto implements UsuarioDAO {

        private final File archivo;
        private final Map<String, Usuario> usuarios = new HashMap<>();

        public UsuarioDAOArchivoTexto(String rutaArchivo) {
            this.archivo = new File(rutaArchivo);
            cargarDesdeArchivo();
            if (usuarios.isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setRol(Rol.ADMINISTRADOR);
                admin.setNombreCompleto("Administrador del Sistema");
                admin.setCorreo("admin@ups.edu.ec");
                admin.setTelefono("0999999999");
                admin.setFechaNacimiento("2000-01-01");
                admin.setRespuestasSeguridad(new ArrayList<>());

                usuarios.put(admin.getUsername(), admin);
                guardarEnArchivo();
            }

        }

        private void cargarDesdeArchivo() {
            if (!archivo.exists()) return;

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(";");
                    if (partes.length < 8) continue;

                    Usuario u = new Usuario();
                    u.setUsername(partes[0]);
                    u.setPassword(partes[1]);
                    u.setRol(Rol.valueOf(partes[2]));
                    u.setNombreCompleto(partes[3]);
                    u.setFechaNacimiento(partes[4]);
                    u.setCorreo(partes[5]);
                    u.setTelefono(partes[6]);

                    String[] pares = partes[7].split(",");
                    List<RespuestaSeguridad> respuestas = new ArrayList<>();
                    for (String par : pares) {
                        String[] datos = par.split("\\|");
                        if (datos.length == 2) {
                            Pregunta p = new Pregunta(0, datos[0].trim());
                            respuestas.add(new RespuestaSeguridad(p, datos[1].trim()));
                        }
                    }
                    u.setRespuestasSeguridad(respuestas);
                    usuarios.put(u.getUsername(), u);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void guardarEnArchivo() {
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
                for (Usuario u : usuarios.values()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(u.getUsername()).append(";")
                            .append(u.getPassword()).append(";")
                            .append(u.getRol().name()).append(";")
                            .append(u.getNombreCompleto()).append(";")
                            .append(u.getFechaNacimiento()).append(";")
                            .append(u.getCorreo()).append(";")
                            .append(u.getTelefono()).append(";");

                    List<RespuestaSeguridad> respuestas = u.getRespuestasSeguridad();
                    for (int i = 0; i < respuestas.size(); i++) {
                        RespuestaSeguridad r = respuestas.get(i);
                        sb.append(r.getPregunta().getTexto()).append("|").append(r.getRespuesta());
                        if (i < respuestas.size() - 1) sb.append(",");
                    }
                    pw.println(sb);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void crear(Usuario usuario) {
            usuarios.put(usuario.getUsername(), usuario);
            guardarEnArchivo();
        }

        @Override
        public Usuario buscarPorUsername(String username) {
            return usuarios.get(username);
        }

        @Override
        public void actualizar(Usuario usuario) {
            usuarios.put(usuario.getUsername(), usuario);
            guardarEnArchivo();
        }

        @Override
        public void eliminar(String username) {
            usuarios.remove(username);
            guardarEnArchivo();
        }

        @Override
        public List<Usuario> listarTodos() {
            return new ArrayList<>(usuarios.values());
        }
        @Override
        public Usuario autenticar(String username, String password) {
            Usuario usuario = usuarios.get(username);
            if (usuario != null && usuario.getPassword().equals(password)) {
                return usuario;
            }
            return null;
        }
        @Override
        public List<Usuario> listarPorRol(Rol rol) {
            List<Usuario> resultado = new ArrayList<>();
            for (Usuario usuario : usuarios.values()) {
                if (usuario.getRol().equals(rol)) {
                    resultado.add(usuario);
                }
            }
            return resultado;
        }
        @Override
        public List<Usuario> buscarPorNombre(String nombre) {
            List<Usuario> resultado = new ArrayList<>();
            for (Usuario usuario : usuarios.values()) {
                if (usuario.getNombreCompleto().toLowerCase().contains(nombre.toLowerCase())) {
                    resultado.add(usuario);
                }
            }
            return resultado;
        }

    }
